let windowId;

chrome.action.onClicked.addListener(function() {
    // If windowId is set, try to get the window
    if (windowId) {
        chrome.windows.get(windowId, { populate: false }, function(win) {
            // If window exists, focus on it
            if (chrome.runtime.lastError) {
                // Error means no such window, so create a new one
                createWindow();
            } else {
                chrome.windows.update(windowId, { focused: true });
            }
        });
    } else {
        // windowId is not set, so create a new window
        createWindow();
    }
});

function createWindow() {
    // Get current window size
    chrome.windows.getCurrent({ populate: true }, function(currentWin) {
        const width = Math.round(currentWin.width / 2);
        const height = Math.round(currentWin.height / 2);

        chrome.windows.create({
            url: 'window.html',
            type: 'panel',
            width: width,
            height: height,
            focused: true  // Makes sure the window is focused when created
        }, function(win) {
            // Save the window ID
            windowId = win.id;

            // Clear windowId when the window is closed
            chrome.windows.onRemoved.addListener(function closedWindow(id) {
                if (id === windowId) {
                    chrome.windows.onRemoved.removeListener(closedWindow);
                    windowId = null;
                }
            });
        });
    });
}
