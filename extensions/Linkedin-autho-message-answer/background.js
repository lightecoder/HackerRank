// Global constant for the answer template
const answerTemplate = "Hi! Nice to meet you!";

// Listen for messages from content scripts
chrome.runtime.onMessage.addListener(function(request, sender, sendResponse) {
    if (request.action === "openNewTabAndRespond" && request.href) {
        openThreadAndRespond(request.href, answerTemplate);
    }
});

function openThreadAndRespond(senderHref, responseText) {
    chrome.tabs.create({url: senderHref}, function(tab) {
        // Store the tab ID and responseText to use later
        chrome.tabs.onUpdated.addListener(function listener(tabId, changeInfo, tab) {
            if (tabId === tab.id && changeInfo.status === 'complete') {
                chrome.tabs.sendMessage(tabId, {
                    action: "sendMessage",
                    text: answerTemplate
                });

                // Remove this listener after it has run to avoid duplicate triggers
                chrome.tabs.onUpdated.removeListener(listener);
            }
        });
    });
}

chrome.runtime.onInstalled.addListener(() => {
  chrome.alarms.create('checkMessages', { periodInMinutes: 0.05 });
});

chrome.alarms.onAlarm.addListener((alarm) => {
  if (alarm.name === 'checkMessages') {
    chrome.tabs.query({active: true, currentWindow: true}, function(tabs) {
      if (tabs[0]) {
        chrome.tabs.sendMessage(tabs[0].id, {action: "checkForNewMessages"});
      }
    });
  }
});
