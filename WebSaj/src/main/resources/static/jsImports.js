$(document).ready(function () {
    $('#dtBasicExample').DataTable();
    $('.dataTables_length').addClass('bs-select');
  });

//async function uploadFile(){
//	console.log("je suis dedans ");
//	let formData = new FormData();
//	formData.append("file",fileUpload.files[0]);
//	let response = await fetch("/createTicket",{
//		method:"POST",
//		body: formData
//	});
//	if(response.status == 200){
//		alert("File successfully uploaded");
//	}
//}
//function init(){
//    document.getElementById('myform').onsubmit = validate;
//}


function onButtonClick(){       
var answer;   
answer = window.confirm("Etes-vous sûr de vouloir ajouter cet employé ?");     
 if (answer == true) {
     return true;
  } else {
     return false;
  }
}
function onButtonDeco(){       
	var answer;   
	answer = window.confirm("Etes-vous sûr de vouloir vous déconnecter ?");     
	 if (answer == true) {
	     return true;
	  } else {
	     return false;
	  }
	}
function onButtonDelete(){       
	var answer;   
	answer = window.confirm("Etes-vous sûr de vouloir supprimer cet employé ?");     
	 if (answer == true) {
	     return true;
	  } else {
	     return false;
	  }
	}

function onButtonUpdate(){
	var answer;   
	answer = window.confirm("Etes-vous sûr de vouloir modifier cet employé ?");     
	 if (answer == true) {
	     return true;
	  } else {
	     return false;
	  }
}
function onButtonCancel(){
	var answer;   
	answer = window.confirm("Etes-vous sûr de vouloir annuler la modification ?");     
	 if (answer == true) {
	     return true;
	  } else {
	     return false;
	  }
}
function onButtonClos(){       
	var answer;   
	answer = window.confirm("Etes-vous sûr de vouloir clôturer ce ticket ?");     
	 if (answer == true) {
	     return true;
	  } else {
	     return false;
	  }
	}
function onButtonPlus(){  
	var list = document.getElementById(list);
	var id = document.getElementById(id);
	for(var i=0; i<list.length; i++) {
		  if(i===id){
			  console.log("Vous avez déjà fait une offre sur ce ticket");
			  var answer;   
				answer = window.alert("Vous avez déjà fait une offre sur ce ticket !");     
				 if (answer == true) {
				     return true;
				  } else {
				     return false;
				  } 
		  }
		}
}