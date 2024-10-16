document.getElementById('registration-form').addEventListener('submit', function(event) {
    event.preventDefault();

    const name = document.getElementById('name').value;
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;


    const userData = {
        name: name,
        email: email,
        password: password
    };


    fetch('http://localhost:8081/api/auth/register', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(userData),
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Error registering user: ' + response.statusText);
            }
            return response.json();
        })
        .then(data => {
            console.log('Server Response:', data);


            const userName = data.name || data.username || userData.name;

            // Display success message
            document.getElementById('message').textContent = `Registration successful! Welcome, ${userName}`;
            document.getElementById('message').style.color = 'green';
            document.getElementById('registration-form').reset();
        })
        .catch(error => {

            document.getElementById('message').textContent = error.message;
            document.getElementById('message').style.color = 'red';
        });
});

