document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('auth-form');
    const toggleLink = document.getElementById('toggle-link');
    const formTitle = document.getElementById('form-title');
    const nameField = document.getElementById('name');
    const submitBtn = document.getElementById('submit-btn');
    const toast = document.getElementById('toast');

    let isLogin = true;

    if (localStorage.getItem('user')) {
        window.location.href = 'services.html';
    }

    toggleLink.addEventListener('click', () => {
        isLogin = !isLogin;
        formTitle.textContent = isLogin ? 'Login' : 'Sign Up';
        submitBtn.textContent = isLogin ? 'Login' : 'Sign Up';
        toggleLink.textContent = isLogin ? 'Sign up' : 'Login';
        nameField.style.display = isLogin ? 'none' : 'block';
    });

    form.addEventListener('submit', async (e) => {
        e.preventDefault();
        const email = document.getElementById('email').value;
        const password = document.getElementById('password').value;
        const name = document.getElementById('name').value;

        const endpoint = isLogin ? '/home/v1/login' : '/home/v1/signup';
        const payload = isLogin ? { email, password } : { name, email, password };

        try {
            const res = await fetch(endpoint, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(payload)
            });
            const data = await res.json();
            if (res.ok) {
                localStorage.setItem('user', JSON.stringify({ name: name || email.split('@')[0], email }));
                showToast(data.message || (isLogin ? 'Login successful' : 'Signup successful'));
                setTimeout(() => window.location.href = 'services.html', 1000);
            } else {
                showToast(data.message || 'Something went wrong');
            }
        } catch (error) {
            console.error(error);
            showToast('An error occurred');
        }
    });

    function showToast(message) {
        toast.textContent = message;
        toast.classList.add('show');
        setTimeout(() => toast.classList.remove('show'), 3000);
    }
});
