from flask import Flask, request, send_from_directory
import subprocess
import os
import shutil

app = Flask(__name__)

BASE_DIR = os.path.dirname(os.path.abspath(__file__))  # backend/
SAMPLES_FOLDER = os.path.join(BASE_DIR, 'samples')  # backend/samples
UPLOADS_FOLDER = os.path.join(BASE_DIR, 'uploads')  # backend/uploads

os.makedirs(SAMPLES_FOLDER, exist_ok=True)
os.makedirs(UPLOADS_FOLDER, exist_ok=True)

@app.route('/samples/<path:filename>')
def serve_sample_files(filename):
    """ Serve files from the samples folder """
    return send_from_directory(SAMPLES_FOLDER, filename)

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

    filepath = os.path.join(UPLOAD_FOLDER, file.filename)
    file.save(filepath)

    try:
        # Run the Java program with the uploaded file
        result = subprocess.run(
            ['java', '-cp', './java/out', 'Main', filepath],
            capture_output=True, text=True
        )
        return result.stdout  # Return the output from the Java program
    except Exception as e:
        return f"Error: {str(e)}", 500

# **NEW: Process Sample Files by Copying to Uploads Folder First**
@app.route('/process_sample/<path:filename>', methods=['POST'])
def process_sample_file(filename):
    sample_path = os.path.join(SAMPLES_FOLDER, filename)
    upload_path = os.path.join(UPLOAD_FOLDER, filename)

    if not os.path.isfile(sample_path):
        return f"Error: Sample file '{filename}' not found.", 404

    # Copy the sample file to the uploads folder
    shutil.copy(sample_path, upload_path)

    # Process the copied file just like a normal uploaded file
    try:
        result = subprocess.run(
            ['java', '-cp', './java/out', 'Main', upload_path],
            capture_output=True, text=True
        )
        return result.stdout
    except Exception as e:
        return f"Error processing file: {str(e)}", 500

if __name__ == '__main__':
    port = int(os.environ.get('PORT', 5000))  # Get PORT from environment
    app.run(debug=False, host='0.0.0.0', port=port)
