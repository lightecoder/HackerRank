let limit = true;
let sendersCount = 1;

// Function to send a response to a message
function sendResponse(senderName) {
    // Find the message thread link corresponding to the senderName
    let messageThreads = document.querySelectorAll('.msg-conversations-container__conversations-list > li');
    let hrefToOpen;

    messageThreads.forEach(thread => {
        let sender = thread.querySelector('.msg-conversation-listitem__participant-names').textContent.trim();
        if (sender === senderName) {
            let link = thread.querySelector('a.msg-conversation-listitem__link');
            if (link) {
                hrefToOpen = link.getAttribute('href');
            }
        }
    });

    // If an href is found, open it in a new tab
    if (hrefToOpen) {
        openLinkInNewTab(hrefToOpen);
    }
}

// Function to open a new tab with the href
function openLinkInNewTab(href) {
    chrome.runtime.sendMessage({action: "openNewTab", href: href});
}

chrome.runtime.onMessage.addListener(function(request, sender, sendResponse) {
    if (request.action === "sendMessage" && request.text) {
        let messageBox = document.querySelector('.msg-form__contenteditable[contenteditable="true"]');
        let sendButton = document.querySelector('.msg-form__send-button');

        if (messageBox && sendButton) {
            // Insert the text
            messageBox.textContent = request.text;

            // Click the send button
            sendButton.click();

            // Close the tab after a short delay to ensure the message is sent
            setTimeout(() => window.close(), 3000);
        }
    }
});

function checkForNewMessages() {
  // Query the DOM for the list of message threads
  let messageThreads = document.querySelectorAll('.msg-conversations-container__conversations-list > li');

  // Retrieve the list of senders we have already responded to
  chrome.storage.sync.get({answeredSenders: []}, function(data) {
    let answeredSenders = data.answeredSenders;
    let processedCount = 0;

    for (let thread of messageThreads) {
      if (limit && processedCount >= sendersCount) {
        break; // Stop processing if the limit is reached
      }

      let senderName = thread.querySelector('.msg-conversation-listitem__participant-names').textContent.trim();
      let newMessageCount = thread.querySelector('.notification-badge__count');

      // Check if there's a new message and if we haven't already responded to this sender
      if (newMessageCount && !answeredSenders.includes(senderName)) {
        // Logic to send a response
        sendResponse(senderName);

        // Add sender to the list of those we've responded to
        addToAnsweredSenders(senderName);

        processedCount++;
      }
    }
  });
}

// Function to add sender to the list of responded senders in synchronized storage
function addToAnsweredSenders(senderName) {
  chrome.storage.sync.get({answeredSenders: []}, function(data) {
    let senders = data.answeredSenders;

    // If the array length reaches 3000, remove the oldest name
    if (senders.length >= 3000) {
      senders.pop(); // Remove the last element (oldest name)
    }

    // Add the new name to the beginning of the array
    senders.unshift(senderName);

    // Save the updated array back to storage.sync
    chrome.storage.sync.set({answeredSenders: senders});
  });
}
