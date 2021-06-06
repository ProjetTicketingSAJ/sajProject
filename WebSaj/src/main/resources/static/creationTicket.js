/* Code pour les tags */
[].forEach.call(document.getElementsByClassName('tags-input'), function (el) {
    let hiddenInput = document.createElement('input'),
        mainInput = document.createElement('input'),
        tags = [];
    
    hiddenInput.setAttribute('type', 'hidden');
    hiddenInput.setAttribute('name', el.getAttribute('data-name'));

    mainInput.setAttribute('type', 'text');
    mainInput.classList.add('main-input');
    mainInput.addEventListener('input', function () {
        let enteredTags = mainInput.value.split(',');
        if (enteredTags.length > 1 ) {
            enteredTags.forEach(function (t) {
                let filteredTag = filterTag(t);
                
               
               if (filteredTag.length > 0 && filteredTag.length <=10){
                    addTag(filteredTag);
                    
                }
                else if (filteredTag.length > 10){
                	var answer;   
                	answer = window.alert("Le tag ne doit pas dépasser 10 caractères");     
                	 if (answer == true) {
                	     return true;
                	  } else {
                	     return false;
                	  }
                }
               
            });
            mainInput.value = '';
        }
    });
   
    mainInput.addEventListener('keydown', function (e) {
        let keyCode = e.which || e.keyCode;
        if (keyCode === 8 && mainInput.value.length === 0 && tags.length > 0) {
            removeTag(tags.length - 1);
        }
    });

    el.appendChild(mainInput);
    el.appendChild(hiddenInput);

    addTag('Spring');
    addTag('JavaFx');

    function addTag (text) {
        let tag = {
            text: text,
            element: document.createElement('span'),
        };
      if(tags.length < 5){
        tag.element.classList.add('tag');
        tag.element.textContent = tag.text;

        let closeBtn = document.createElement('span');
        closeBtn.classList.add('close');
        closeBtn.addEventListener('click', function () {
            removeTag(tags.indexOf(tag));
        });
        tag.element.appendChild(closeBtn);
    
        tags.push(tag);
        
        el.insertBefore(tag.element, mainInput);

        refreshTags();
      }
      else{
    	  var answer;   
      	answer = window.alert("Nombre de tags maximum atteint");     
      	 if (answer == true) {
      	     return true;
      	  } else {
      	     return false;
      	  }
      }
       
    }

    function removeTag (index) {
        let tag = tags[index];
        tags.splice(index, 1);
        el.removeChild(tag.element);
        refreshTags();
    }

    function refreshTags () {
        let tagsList = [];
        tags.forEach(function (t) {
            tagsList.push(t.text);
        });
        hiddenInput.value = tagsList.join(',');
    }

    function filterTag (tag) {
        return tag.replace(/[^\w -]/g, '').trim().replace(/\W+/g, '-');
    }
});

/* Code pagination */
$(document).ready(function () {
    $('#dtBasicExample').DataTable();
    $('.dataTables_length').addClass('bs-select');
  });


/* code pour la validation */
//Example starter JavaScript for disabling form submissions if there are invalid fields
(function () {
  'use strict'

  // Fetch all the forms we want to apply custom Bootstrap validation styles to
  var forms = document.querySelectorAll('.needs-validation')

  // Loop over them and prevent submission
  Array.prototype.slice.call(forms)
    .forEach(function (form) {
      form.addEventListener('submit', function (event) {
        if (!form.checkValidity()) {
          event.preventDefault()
          event.stopPropagation()
        }

        form.classList.add('was-validated')
      }, false)
    })
})()

