/*-----------html/jsp stub references---------*/
$("#foo").load("templates/footer.html");
$("#bar").load("templates/page_header.jsp");
$("#mod").load("templates/login_register_modal.html");
/*--------------------------------------------*/

//-----------sign-in/register modal---------------
$(function() {
    $(document).on("click","#login-form-link" , function(e) {
        $("#login-form").delay(100).fadeIn(100);
        $("#register-form").fadeOut(100);
        $('#register-form-link').removeClass('active');
        $(this).addClass('active');
        e.preventDefault();
    });
    $(document).on("click","#register-form-link" , function(e) {
        $("#register-form").delay(100).fadeIn(100);
        $("#login-form").fadeOut(100);
        $('#login-form-link').removeClass('active');
        $(this).addClass('active');
        e.preventDefault();
    });
});