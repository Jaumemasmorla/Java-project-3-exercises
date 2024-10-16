document.getElementById('login-form').addEventListener('submit', function(event) {
    event.preventDefault();

    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;


    const loginData = {
        email: email,
        password: password
    };


    fetch('http://localhost:8081/api/auth/authenticate', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(loginData),
    })
        .then(response => {
            if (!response.ok) {
                return response.json().then(errData => {
                    throw new Error(errData.message || 'Error authenticating user');
                });
            }
            return response.json();
        })
        .then(data => {

            const token = data.jwtToken;


            document.getElementById('token-textarea').value = token;
            document.getElementById('token-modal').style.display = 'block';


            localStorage.setItem('jwtToken', token);

            document.getElementById('login-form').reset();
        })
        .catch(error => {
            document.getElementById('message').textContent = error.message;
            document.getElementById('message').style.color = 'red';
        });


    document.getElementById('close-modal').addEventListener('click', function () {
        document.getElementById('token-modal').style.display = 'none';
    });


    document.getElementById('copy-button').addEventListener('click', function () {
        const tokenTextarea = document.getElementById('token-textarea');
        tokenTextarea.select();
        document.execCommand('copy');
        alert('Token copied to clipboard!');
    });
});
