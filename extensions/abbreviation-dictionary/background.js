chrome.action.onClicked.addListener(function() {
    // Try to get the window ID from storage
    chrome.storage.local.get('windowId', function(data) {
        if (data.windowId) {
            // If windowId is stored, try to get the window
            chrome.windows.get(data.windowId, { populate: false }, function(win) {
                if (chrome.runtime.lastError) {
                    // Error means no such window, so create a new one
                    createWindow();
                } else {
                    chrome.windows.update(data.windowId, { focused: true });
                }
            });
        } else {
            // windowId is not stored, so create a new window
            createWindow();
        }
    });
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
            focused: true
        }, function(win) {
            // Save the window ID in storage
            chrome.storage.local.set({ 'windowId': win.id });

            // Clear windowId in storage when the window is closed
            chrome.windows.onRemoved.addListener(function closedWindow(id) {
                if (id === win.id) {
                    chrome.windows.onRemoved.removeListener(closedWindow);
                    chrome.storage.local.remove('windowId');
                }
            });
        });
    });
}
