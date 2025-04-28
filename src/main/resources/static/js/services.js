document.addEventListener('DOMContentLoaded', async function() {
    const userNameElement = document.getElementById('user-name');
    const logoutButton = document.getElementById('logout-btn');
    const servicesForm = document.getElementById('services-form');
    const generateTokenButton = document.getElementById('generate-token-button');
    const tokenResult = document.getElementById('token-result');
    const toast = document.getElementById('toast');

    const user = JSON.parse(localStorage.getItem('user'));
    if (!user) {
        window.location.href = 'index.html';
        return;
    }
    userNameElement.textContent = user.name || 'User';

    logoutButton.addEventListener('click', () => {
        localStorage.removeItem('user');
        window.location.href = 'index.html';
    });

    function showToast(message) {
        toast.textContent = message;
        toast.classList.add('show');
        setTimeout(() => {
            toast.classList.remove('show');
        }, 3000);
    }

    async function fetchServices() {
        try {
            const response = await fetch('/home/v1/services');
            const data = await response.json();
            if (response.ok && data.success) {
                renderServices(data.data);
            } else {
                showToast('Failed to load services.');
            }
        } catch (error) {
            console.error(error);
            showToast('An error occurred while loading services.');
        }
    }

    function renderServices(services) {
        servicesForm.innerHTML = '';
        services.forEach(service => {
            const label = document.createElement('label');
            label.className = 'service-radio';
            label.innerHTML = `
                <input type="radio" name="service" value="${service}">
                ${service}
            `;
            servicesForm.appendChild(label);
        });
    }

    async function generateToken() {
        const selectedService = document.querySelector('input[name="service"]:checked');
        if (!selectedService) {
            showToast('Please select a service.');
            return;
        }

        try {
            const response = await fetch('/token/v1/generate', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ email: user.email, service: selectedService.value })
            });
            const data = await response.json();
            if (response.ok && data.success) {
                const token = data.data.token;
                tokenResult.innerHTML = `
                    <p>Your token for <strong>${selectedService.value}</strong>:</p>
                    <div class="token-box">${token}</div>
                    <button class="copy-btn" id="copy-btn">Copy</button>
                `;

                const copyBtn = document.getElementById('copy-btn');
                copyBtn.addEventListener('click', () => {
                    navigator.clipboard.writeText(token);
                    showToast('Token copied to clipboard!');
                });

            } else {
                showToast('Failed to generate token.');
            }
        } catch (error) {
            console.error(error);
            showToast('Error generating token.');
        }
    }

    generateTokenButton.addEventListener('click', generateToken);

    fetchServices();
});
