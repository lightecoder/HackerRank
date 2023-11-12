chrome.runtime.onMessage.addListener((request, sender, sendResponse) => {
  if (request.action === "sendTextToContentScript") {
    chrome.scripting.executeScript({
      target: { tabId: request.tabId },
      function: insertTextIntoPage,
      args: [request.text]
    });
  }
});

function insertTextIntoPage(text) {
    console.info(text);
  // Logic to insert text into the ChatGPT input field
  // This will vary depending on the structure of the webpage
}

let recognition;
let recognizing = false;
const button = document.getElementById('start-stop-recording');

// Initialize the Web Speech API for voice recognition
function initSpeechRecognition() {
  recognition = new window.webkitSpeechRecognition();
  recognition.continuous = true;
  recognition.interimResults = false;
  recognition.lang = 'en-US'; // Set the language as needed

  recognition.onstart = function() {
    recognizing = true;
    // Update button to show recording state
  };

  recognition.onerror = function(event) {
    console.error('Speech recognition error detected:', event.error, 'Additional details:', event.message);
  };

  recognition.onend = function() {
    recognizing = false;
    // Update button to show non-recording state
  };

  recognition.onresult = function(event) {
    let final_transcript = '';
    for (let i = event.resultIndex; i < event.results.length; ++i) {
      if (event.results[i].isFinal) {
        final_transcript += event.results[i][0].transcript;
      }
    }

    if (final_transcript) {
      // Send the final transcript to the background script
      chrome.tabs.query({active: true, currentWindow: true}, tabs => {
        chrome.runtime.sendMessage({
          action: "sendTextToContentScript",
          text: final_transcript,
          tabId: tabs[0].id
        });
      });
    }
  };
}

// Attach event listeners to the button
button.addEventListener('click', () => {
  if (!recognizing) {
    recognition.start();
  } else {
    recognition.stop();
  }
});

// Initialize the speech recognition
initSpeechRecognition();
