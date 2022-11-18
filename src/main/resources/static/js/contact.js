/**
 * 
 */
 
 document.addEventListener('DOMContentLoaded', function(event) {
  const targetButton = document.getElementById('send_button');
  const triggerCheckbox = document.querySelector('input[name="agree"]');

  targetButton.disabled = true;
  targetButton.classList.add('is-inactive');

  triggerCheckbox.addEventListener('change', function() {
    if (this.checked) {
      targetButton.disabled = false;
      targetButton.classList.remove('is-inactive');
      targetButton.classList.add('is-active');
    } else {
      targetButton.disabled = true;
      targetButton.classList.remove('is-active');
      targetButton.classList.add('is-inactive');
    }
  }, false);
}, false);