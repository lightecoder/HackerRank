function extractLinks() {
    console.log("extractLinks in content.js")
  const links = Array.from(document.querySelectorAll('a[href^="/messaging/thread/"]'))
    .map(link => link.getAttribute('href'));
  chrome.runtime.sendMessage({ action: "openLinks", links: links });
}

// Run the function when the page is loaded
extractLinks();
