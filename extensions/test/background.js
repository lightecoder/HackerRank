chrome.runtime.onMessage.addListener((message, sender, sendResponse) => {
  if (message.action === "openLinks") {
    handleLinks(message.links);
  }
});

async function handleLinks(links) {
  for (const link of links) {
    const tab = await chrome.tabs.create({ url: `https://www.linkedin.com${link}`, active: false });
    await new Promise(resolve => setTimeout(resolve, 3000)); // wait for 3 seconds
    chrome.tabs.remove(tab.id);
  }
}
