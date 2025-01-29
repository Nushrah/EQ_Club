document.getElementById('fileForm').addEventListener('submit', async (e) => {
    e.preventDefault();

    const formData = new FormData();
    const fileInput = document.getElementById('fileUpload');

    if (!fileInput.files[0]) {
        document.getElementById('output').innerText = "Error: No file selected.";
        return;
    }
    formData.append('file', fileInput.files[0]);

    try {
        // Send the uploaded file to the backend for processing
        const response = await fetch('http://127.0.0.1:5000/process', {
            method: 'POST',
            body: formData,
        });

        const output = await response.text();
        document.getElementById('output').innerText = output;
    } catch (error) {
        document.getElementById('output').innerText = `Error: ${error.message}`;
    }
});

// Load and process sample files by sending them to /process_sample/
document.getElementById('sample1').addEventListener('click', async (e) => {
    e.preventDefault();
    await processSampleFile('sample1.txt');
});

document.getElementById('sample2').addEventListener('click', async (e) => {
    e.preventDefault();
    await processSampleFile('sample2.txt');
});

// Helper function to process sample files correctly
async function processSampleFile(filename) {
    try {
        const response = await fetch(`http://127.0.0.1:5000/process_sample/${filename}`, {
            method: 'POST'
        });

        const output = await response.text();
        document.getElementById('output').innerText = output;
    } catch (error) {
        document.getElementById('output').innerText = `Error processing sample file: ${error.message}`;
    }
}
