// Use the exact Heroku domain instead of 127.0.0.1
const BASE_URL = "https://equipmentclub-6aa6f95c269e.herokuapp.com";

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
        // Call the backend on Heroku
        const response = await fetch(`${BASE_URL}/process`, {
            method: 'POST',
            body: formData,
        });

        const output = await response.text();
        document.getElementById('output').innerText = output;
    } catch (error) {
        document.getElementById('output').innerText = `Error: ${error.message}`;
    }
});

// Sample file buttons
document.getElementById('sample1').addEventListener('click', async (e) => {
    e.preventDefault();
    await processSampleFile('sample1.txt');
});

document.getElementById('sample2').addEventListener('click', async (e) => {
    e.preventDefault();
    await processSampleFile('sample2.txt');
});

async function processSampleFile(filename) {
    try {
        const response = await fetch(`${BASE_URL}/process_sample/${filename}`, {
            method: 'POST'
        });
        const output = await response.text();
        document.getElementById('output').innerText = output;
    } catch (error) {
        document.getElementById('output').innerText = `Error processing sample file: ${error.message}`;
    }
}
