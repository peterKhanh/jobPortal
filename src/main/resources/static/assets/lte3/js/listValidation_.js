// Document is ready 
$(document).ready(function () { 
 
	$("#titlecheck").hide(); 
	let titleError = true; 
	$("#title").keyup(function () { 
		//validateTitle(); 
		validateTextInput(element_title, element_title_error, "Title not Blank")
	}); 


	function validateTitle(message) { 
		let titleValue = $(element_title).val(); 
		if (titleValue.length == "") { 
			$(element_title_error).html(message); 

			$(element_title_error).show(); 
			titleError = false; 
			return false; 
		} else {
			$(element_title_error).hide(); 
			titleError = true; 
			return true; 
		} 
	} 
	/*
	Veryfy in put
	
	*/
	function validateInput(input, error, message) { 
		let titleValue = $(input).val(); 
		if (titleValue.length == "") { 
			$(error).html(message); 
			$(error).css("color", "red"); 
			$(error).show(); 
			return false; 
		} else {
			$(error).hide(); 
			return true; 
		} 
	} 

    const element_title = document.getElementById("title");
    const element_title_error = document.getElementById("title_err_message");
  
 	const e_file = document.getElementById("file");
    const e_file_error = document.getElementById("file_err_message");

 $('#save_slide').click(function(e) {
        e.preventDefault();
       titleError= validateInput(element_title, element_title_error, "Title not Blank")
       fileError= validateInput(e_file, e_file_error, "Select your images")

        if (titleError == true && fileError == true){
			$("#frmSlide").submit();
		}
    });


 $('#update_slide').click(function(e) {
        e.preventDefault();
       titleError= validateInput(element_title, element_title_error, "Title not Blank")
     //  fileError= validateInput(e_file, e_file_error, "Select your images")

        if (titleError == true){
			$("#frmSlide").submit();
		}
    });
 
 
// Save and Update Category    
 	const e_category_name = document.getElementById("category_name");
    const e_category_name_error = document.getElementById("category_name_message");

 $('#btn_save_category').click(function(e) {
        e.preventDefault();
        
       error_catetegory_name= validateInput(e_category_name, e_category_name_error, "Category Name not Blank")
          if (error_catetegory_name == true){
			$("#frm_category").submit();
		}
    });


 
// Save and Update Blog  
 	const e_blog_title = document.getElementById("title");
    const e_blog_title_error = document.getElementById("title_message");

 $('#btn_save_blog').click(function(e) {
        e.preventDefault();
        
       error_title= validateInput(e_blog_title, e_blog_title_error, "Blog Title not Blank")
          if (error_title == true){
			$("#frm_blog").submit();
		}
    });

 
// Save and Update Enterprise  
 	const e_enterprise_name = document.getElementById("enterprise-name");
    const e_enterprise_name_error = document.getElementById("enterprise-name_message");

 $('#btn_save_enterprise').click(function(e) {
        e.preventDefault();
        
       error_title= validateInput(e_enterprise_name, e_enterprise_name_error, "Enterprise name  not Blank")
          if (error_title == true){
			  alert('Submitting');
			$("#frm_enterprise").submit();
		}
    });


 
});


 

