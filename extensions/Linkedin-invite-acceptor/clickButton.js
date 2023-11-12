function clickAcceptButtons() {
  var acceptButtons = document.querySelectorAll('button[aria-label^="Accept"]');
  acceptButtons.forEach(function(button) {
    button.click();
  });
}

// Run initially
clickAcceptButtons();

// Polling for dynamic content
setInterval(clickAcceptButtons, 60000); // checks every 60 seconds
