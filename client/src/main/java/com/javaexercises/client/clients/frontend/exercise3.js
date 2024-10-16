document.addEventListener('DOMContentLoaded', function() {
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


        const payload = JSON.parse(atob(token.split('.')[1]));
        const email = payload.sub;
        const roles = payload.roles;


        if (!roles.includes("ROLE_ADMIN")) {
            alert('You do not have permission to access this functionality.');
            return;
        }


        document.getElementById('token-modal').style.display = 'none';
        document.getElementById('bracket-container').style.display = 'block';
    });

    document.getElementById('check-brackets').addEventListener('click', function() {
        const expression = document.getElementById('bracket-input').value;


        const token = localStorage.getItem('jwtToken');

        if (!token) {
            alert('Please authenticate first.');
            return;
        }


        const payload = JSON.parse(atob(token.split('.')[1]));
        const roles = payload.roles;


        if (!roles.includes("ROLE_ADMIN")) {
            alert('You do not have permission to access this functionality.');
            return;
        }


        fetch('http://localhost:8081/api/v1/client/validate-brackets', {
            method: 'POST',
            headers: {
                'Content-Type': 'text/plain',
                'Authorization': 'Bearer ' + token,
            },
            body: expression
        })
            .then(response => {
                if (!response.ok) {
                    return response.text().then(errMsg => {
                        throw new Error(errMsg || 'Error validating brackets');
                    });
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
});
