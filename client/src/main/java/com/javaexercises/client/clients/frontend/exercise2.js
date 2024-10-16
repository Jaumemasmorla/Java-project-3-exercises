
document.getElementById('open-modal').addEventListener('click', function() {
    document.getElementById('token-modal').style.display = 'block';
});

document.getElementById('close-modal').addEventListener('click', function() {
    document.getElementById('token-modal').style.display = 'none';
});

document.getElementById('submit-token').addEventListener('click', function() {
    const token = document.getElementById('token-textarea').value;


    if (!token) {
        alert('Please paste a valid token.');
        return;
    }


    localStorage.setItem('jwtToken', token);


    document.getElementById('token-modal').style.display = 'none';
    document.getElementById('palindrome-container').style.display = 'block';
});

document.getElementById('check-palindrome').addEventListener('click', function() {
    const word = document.getElementById('palindrome-input').value;


    const token = localStorage.getItem('jwtToken');

    if (!token) {
        alert('Please authenticate first.');
        return;
    }

    fetch('http://localhost:8081/api/v1/client/palindrome', {
        method: 'POST',
        headers: {
            'Content-Type': 'text/plain',
            'Authorization': 'Bearer ' + token,
        },
        body: word
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('KO' + response.statusText);
            }
            return response.text();
        })
        .then(data => {
            document.getElementById('result').textContent = data;
        })
        .catch(error => {
            document.getElementById('result').textContent = error.message;
        });
});
