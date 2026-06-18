document.addEventListener('DOMContentLoaded', () => {
    const shortenBtn = document.getElementById('shortenBtn');
    const urlInput = document.getElementById('urlInput');
    const resultBox = document.getElementById('resultBox');
    const shortenedUrl = document.getElementById('shortenedUrl');
    const copyBtn = document.getElementById('copyBtn');
    const insightsLink = document.getElementById('insightsLink');

    if (shortenBtn) {
        shortenBtn.addEventListener('click', async () => {
            const url = urlInput.value;
            if (!url) return alert('Please enter a URL');

            try {
                const response = await fetch('/api/shorten', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify({ url })
                });

                if (response.ok) {
                    const code = await response.text();
                    const fullShortUrl = `${window.location.origin}/${code}`;
                    shortenedUrl.value = fullShortUrl;
                    insightsLink.href = `/insights.html?code=${code}`;
                    resultBox.classList.remove('hidden');
                } else {
                    const err = await response.text();
                    alert('Error: ' + err);
                }
            } catch (error) {
                console.error('Fetch error:', error);
                alert('Something went wrong!');
            }
        });
    }

    if (copyBtn) {
        copyBtn.addEventListener('click', () => {
            shortenedUrl.select();
            document.execCommand('copy');
            copyBtn.textContent = 'Copied!';
            setTimeout(() => { copyBtn.textContent = 'Copy'; }, 2000);
        });
    }
});

async function fetchInsights(code) {
    try {
        const response = await fetch(`/api/insights/${code}`);
        if (response.ok) {
            const data = await response.json();
            document.getElementById('displayShortCode').textContent = data.shortCode;
            document.getElementById('displayClicks').textContent = data.clickCount;
            document.getElementById('displayOriginalUrl').textContent = data.mainUrl;
            document.getElementById('displayCreatedAt').textContent = new Date(data.createdAt).toLocaleString();
        } else {
            alert('Could not fetch insights');
        }
    } catch (error) {
        console.error('Insights error:', error);
    }
}
