// Document is ready 
$(document).ready(function() {

	$("#titlecheck").hide();
	let titleError = true;
	$("#title").keyup(function() {
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
		titleError = validateInput(element_title, element_title_error, "Title not Blank")
		fileError = validateInput(e_file, e_file_error, "Select your images")

		if (titleError == true && fileError == true) {
			$("#frmSlide").submit();
		}
	});


	$('#update_slide').click(function(e) {
		e.preventDefault();
		titleError = validateInput(element_title, element_title_error, "Title not Blank")
		//  fileError= validateInput(e_file, e_file_error, "Select your images")

		if (titleError == true) {
			$("#frmSlide").submit();
		}
	});


	// Save and Update Category    
	const e_category_name = document.getElementById("category_name");
	const e_category_name_error = document.getElementById("category_name_message");

	$('#btn_save_category').click(function(e) {
		e.preventDefault();

		error_catetegory_name = validateInput(e_category_name, e_category_name_error, "Category Name not Blank")
		if (error_catetegory_name == true) {
			$("#frm_category").submit();
		}
	});



	// Save and Update Blog  
	const e_blog_title = document.getElementById("title");
	const e_blog_title_error = document.getElementById("title_message");

	$('#btn_save_blog').click(function(e) {
		e.preventDefault();

		error_title = validateInput(e_blog_title, e_blog_title_error, "Blog Title not Blank")
		if (error_title == true) {
			$("#frm_blog").submit();
		}
	});




	// Thêm mới người sử dụng Admin User
	// 

	$(function() {
		$.validator.setDefaults({
			submitHandler: function() {
				$("#frmAddUser").submit();
			}
		});
		$('#frmAddUser').validate({
			rules: {
				fullName: {
					required: true,
				},
				address: {
					required: true,
				},
				userName: {
					required: true,
					remote: "/candidate/checkUsername",

				},
				email: {
					required: true,
					   email: true,
					   					remote: "/candidate/checkemail",

				},
				passWord: {
					minlength: 5,
					required: true,
				},
				retypepassword: {
            		equalTo: '#passWord'
				},

			},
			messages: {
				fullName: {
					required: "Nhập họ và tên ",
				},
				address: {
					required: "Nhập địa chỉ",
				},
				email: {
					required: "Nhập email",
					email: "Định dạng Email chưa đúng",
					remote: "Email đã sử dụng, hãy nhập email khác",
				},
				userName: {
           		   required: "Xin vui lòng nhập tên đăng nhập hợp lệ",
           		   					remote: "Tên đăng nhập đã tồn tại",

				},
      			passWord: {
    			  	minlength: "Cần đủ 5 ký tự",
            	  	required: "Hãy nhập mật khẩu của bạn",
     		 	},
     			retypepassword: {
            	  equalTo: "Mật khẩu chưa khớp",
     		 	},

			},
			errorElement: 'span',
			errorPlacement: function(error, element) {
				error.addClass('invalid-feedback');
				element.closest('.form-group').append(error);
			},
			highlight: function(element, errorClass, validClass) {
				$(element).addClass('is-invalid');
			},
			unhighlight: function(element, errorClass, validClass) {
				$(element).removeClass('is-invalid');
			}
		});
	});



	// This function Validate form Register new Account
	// 
	$(function() {
		$.validator.setDefaults({
			submitHandler: function() {
				$("#frm_edituser").submit();
			}
		});
		$('#frm_edituser').validate({
			rules: {
				fullName: {
					required: true,
				},
				email: {
					required: true,
					      email: true

				},
				address: {
					required: true,
				},
				

			},
			messages: {
				fullName: {
					required: "Nhập họ và tên của ứng viên",
				},
				email: {
					required: "Hãy nhập Email của bạn",
					email: "Email chưa đúng định dạng",
				},
				address: {
					required: "Hãy nhập địa chỉ của bạn",
				},
			
			},
			errorElement: 'span',
			errorPlacement: function(error, element) {
				error.addClass('invalid-feedback');
				element.closest('.form-group').append(error);
			},
			highlight: function(element, errorClass, validClass) {
				$(element).addClass('is-invalid');
			},
			unhighlight: function(element, errorClass, validClass) {
				$(element).removeClass('is-invalid');
			}
		});
	});


	// This function Validate form Add and Update Danh muc

	$(function() {
		$.validator.setDefaults({
			submitHandler: function() {
				$("#frmList").submit();
			}
		});
		$('#frmList').validate({
			rules: {
				name: { required: true, },
		
			},
			messages: {
				name: "Nhập tên danh muc",

			
			},
			errorElement: 'span',
			errorPlacement: function(error, element) {
				error.addClass('invalid-feedback');
				element.closest('.form-group').append(error);
			},
			highlight: function(element, errorClass, validClass) {
				$(element).addClass('is-invalid');
			},
			unhighlight: function(element, errorClass, validClass) {
				$(element).removeClass('is-invalid');
			}
		});
	});
	


	// This function Validate form Add and Update Enterprise

	$(function() {
		$.validator.setDefaults({
			submitHandler: function() {
				$("#frm_enterprise").submit();
			}
		});
		$('#frm_enterprise').validate({
			rules: {
				name: { required: true, },
				numberOfEmployee: {
					required: true
				},
				address: {
					required: true
					,
				},
				introduction: {
					required: true
				},
			},
			messages: {
				name: "Nhập tên doanh nghiệp tuyển dụng",

				numberOfEmployee: {
					required: "Nhập quy mô công ty",
				},
				address: {
					required: "Nhập địa chỉ trụ sở",
				},
				introduction: {
					required: "Cần nhập giới thiệu công ty",
				},
			},
			errorElement: 'span',
			errorPlacement: function(error, element) {
				error.addClass('invalid-feedback');
				element.closest('.form-group').append(error);
			},
			highlight: function(element, errorClass, validClass) {
				$(element).addClass('is-invalid');
			},
			unhighlight: function(element, errorClass, validClass) {
				$(element).removeClass('is-invalid');
			}
		});
	});





	// This function Validate form Add and Update Job

	$(function() {
		$.validator.setDefaults({
			submitHandler: function() {
				$("#frm_job").submit();
			}
		});
		$('#frm_job').validate({
			rules: {
				enterprise: { required: true, },
				title: { required: true, },
				jobCategory: { required: true, },
				workingModel: { required: true, },
				numberOfRecruitement: {
					required: true,
					number: true
				},
				expiredDate: {
					required: true,
					date: true
				},
				salary: {
					required: true
				},
				workingAddress: {
					required: true
				},
				reponsibility: {
					required: true
				},
				description: {
					required: true
				},
				benefit: {
					required: true
				},
			},
			messages: {
				enterprise: "Chọn tên doanh nghiệp tuyển dụng",
				title: {
					required: "Hãy nhập tiêu đề công việc",
				},
				jobCategory: {
					required: "Hãy nhập lĩnh vực",
				},
				workingModel: {
					required: "Loại hình công việc",
				},
				numberOfRecruitement: {
					required: "Nhập số lượng cần tuyển",
					number: "Dữ liệu là dạng số"
				},
				expiredDate: {
					required: "Hãy chọn ngày hết hạn đăng tuyển",
					date: "Đinh dạng chưa đúng dd/MM/yyyy"
				},
				salary: {
					required: "Cần nhập mức lương",
				},
				workingAddress: {
					required: "Cần nhập địa điểm làm việc",
				},
				reponsibility: {
					required: "Cần nhập yêu cầu công việc",
				},
				description: {
					required: "Cần nhập mô tả công việc",
				},
				benefit: {
					required: "Cần nhập quyền lợi người lao động",
				}

			},
			errorElement: 'span',
			errorPlacement: function(error, element) {
				error.addClass('invalid-feedback');
				element.closest('.form-group').append(error);
			},
			highlight: function(element, errorClass, validClass) {
				$(element).addClass('is-invalid');
			},
			unhighlight: function(element, errorClass, validClass) {
				$(element).removeClass('is-invalid');
			}
		});
	});



	// This function Validate form Login
	// 
	$(function() {
		$.validator.setDefaults({
			submitHandler: function() {
				$("#frm_login").submit();
			}
		});
		$('#frm_login').validate({
			rules: {
				username: {
					required: true,

				},
				password: {
					required: true,
				},

			},
			messages: {
				username: {
					required: "Xin vui lòng nhập tên đăng nhập hợp lệ",
				},
				password: {
					required: "Hãy nhập mật khẩu của bạn",
				},

			},
			errorElement: 'span',
			errorPlacement: function(error, element) {
				error.addClass('invalid-feedback');
				element.closest('.form-group').append(error);
			},
			highlight: function(element, errorClass, validClass) {
				$(element).addClass('is-invalid');
			},
			unhighlight: function(element, errorClass, validClass) {
				$(element).removeClass('is-invalid');
			}
		});
	});









});




