from flask import Flask, request, send_from_directory, send_file, abort
import subprocess
import os
import shutil
from flask_cors import CORS

app = Flask(__name__)
CORS(app)

# Use /tmp/ because Heroku allows read/write access here
TMP_SAMPLES = "/tmp/samples"
TMP_UPLOADS = "/tmp/uploads"

# Ensure directories exist on startup
os.makedirs(TMP_SAMPLES, exist_ok=True)
os.makedirs(TMP_UPLOADS, exist_ok=True)

# Define the source directory inside the repo
BASE_DIR = os.path.dirname(os.path.abspath(__file__))  # Points to 'backend/'
JAVA_OUT_DIR = os.path.join(BASE_DIR, '..', 'java', 'out')  # => Equipment_Club/java/out
SAMPLES_SOURCE = os.path.join(BASE_DIR, 'samples')

SAMPLES_SOURCE = os.path.join(BASE_DIR, 'samples')
if os.path.exists(SAMPLES_SOURCE) and os.listdir(SAMPLES_SOURCE):
    print("✅ Copying sample files to /tmp/samples/...")
    for file_name in os.listdir(SAMPLES_SOURCE):
        src_path = os.path.join(SAMPLES_SOURCE, file_name)
        dest_path = os.path.join(TMP_SAMPLES, file_name)
        if os.path.isfile(src_path):
            shutil.copy2(src_path, dest_path)  # Copy files with metadata
            
            # Force world-readable permissions: -rw-r--r--
            os.chmod(dest_path, 0o644)

    print("✅ Sample files copied successfully.")
else:
    print("❌ WARNING: 'backend/samples/' is missing or empty. No files copied.")


@app.route('/samples/<path:filename>')
def serve_sample_files(filename):
    """ Serve sample files from /tmp/samples """
    file_path = os.path.join(TMP_SAMPLES, filename)
    if os.path.exists(file_path) and os.path.isfile(file_path):
        return send_file(file_path, as_attachment=True)
    return abort(404, description=f"Sample file '{filename}' not found.")

@app.route('/uploads/<path:filename>')
def serve_uploaded_files(filename):
    """ Serve uploaded files from /tmp/uploads """
    file_path = os.path.join(TMP_UPLOADS, filename)
    if os.path.exists(file_path) and os.path.isfile(file_path):
        return send_file(file_path, as_attachment=True)
    return abort(404, description=f"Uploaded file '{filename}' not found.")

# Serve the frontend when the root URL is accessed
@app.route('/')
def index():
    return send_from_directory('../frontend', 'index.html')

# Serve static files (CSS, JS, etc.)
@app.route('/css/<path:filename>')
def serve_css(filename):
    return send_from_directory('../frontend/css', filename)

@app.route('/js/<path:filename>')
def serve_js(filename):
    return send_from_directory('../frontend/js', filename)

# Serve the favicon
@app.route('/favicon.ico')
def favicon():
    return send_from_directory('../frontend', 'favicon.ico')

# Handle file uploads and process them
@app.route('/process', methods=['POST'])
def process_file():
    if 'file' not in request.files:
        return "No file uploaded", 400

    file = request.files['file']
    if file.filename == '':
        return "No file selected", 400

    filepath = os.path.join(TMP_UPLOADS, file.filename)
    file.save(filepath)

    try:
        # Run the Java program with the uploaded file
        result = subprocess.run(['java','-cp', JAVA_OUT_DIR,'Main',filepath],
            capture_output=True, text=True
        )
        return result.stdout  # Return the output from the Java program
    except Exception as e:
        return f"Error: {str(e)}", 500
    
@app.route("/debug/which_java")
def which_java():
    import subprocess
    result = subprocess.run(["which", "java"], capture_output=True, text=True)
    return f"which java:\nstdout={result.stdout}\nstderr={result.stderr}\n"


@app.route('/process_sample/<path:filename>', methods=['POST'])
def process_sample_file(filename):
    """ Copy sample file to /tmp/uploads/ and process it """
    sample_path = os.path.join(TMP_SAMPLES, filename)
    upload_path = os.path.join(TMP_UPLOADS, filename)

    if not os.path.isfile(sample_path):
        return f"Error: Sample file '{filename}' not found.", 404

    # Copy the sample file to /tmp/uploads
    shutil.copy(sample_path, upload_path)

    # Process the copied file just like a normal uploaded file
    try:
        result = subprocess.run(['java','-cp', JAVA_OUT_DIR,'Main',upload_path],
            capture_output=True, text=True
        )
        return result.stdout
    except Exception as e:
        return f"Error processing file: {str(e)}", 500

if __name__ == '__main__':
    port = int(os.environ.get('PORT', 5000))  # Get PORT from environment
    app.run(debug=False, host='0.0.0.0', port=port)