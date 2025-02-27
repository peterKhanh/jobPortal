$(document).ready(function(){ 

/*
		$(".btn-open-dialog").click(function(){
			var id = $(this).closest("ul").attr("data-id");
			// $modal.find('[data-value="firstname"]').text(firstname );
			$("#myModal #id").val(id);
			$("#myModal #name").val(id);

		});

*/

	
	/*
	Nút Gủi Job cho Ứng viên khi xem hồ sơ

	*/
	$(".btn-sendEmail").click(function(){
		var jobId = $(this).closest("tr").attr("jobId"); //userEmail
		var jobTitle = $(this).closest("tr").attr("jobTitle"); 
		var userEmail = $(this).closest("tr").attr("userEmail");  
		var jobStatus = $(this).closest("tr").attr("jobStatus"); 
		
		
//	alert(jobTitle  + "  -  "  + userEmail + "  -  "  + jobStatus);
	/*
		$.toast({
			heading: 'Success',
			text: jobTitle,
			icon: 'success',
			position: 'top-center'
			//	loaderBg: '#9EC600'  // To change the background
		})
		*/
			var form_data = {
				jobId: jobId,
				jobTitle : jobTitle,
				userEmail : userEmail
			}
			var jsonString = JSON.stringify(form_data);
			/*
			//  url:'/shop/cart-update/${id}/${qty}',
			url:"/shop/cart-update/"+id+"/"+qty,

			*/
		$.ajax({
			url:"/admin/sendJob/"+jobId+"/"+userEmail,
			data:form_data,
		//	contentType: "application/json; charset=utf-8",
    	//	dataType: "String",
    		success:function(response){
				if(response){
					showToast("Success","Đã gửi email đến ứng viên", "success");
					/*$.toast({
							heading: 'Success',
							text: "Thanh cong",
							icon: 'success',
							position: 'top-center'
							//	loaderBg: '#9EC600'  // To change the background
							})	*/
				}else{
					alert("Khong gui dc")	
				}
			}
		})
		
	})
	
	function showToast(type, text, icon){
		
		$.toast({
			heading: type, 
			text: text,
			icon: icon,
			position: 'top-center'
			//	loaderBg: '#9EC600'  // To change the background
		})	
	}
	
	
	
});