<%@include file="template/header.jsp" %>

<div class="jumbotron">
    <h1 class="text-center">Login</h1>  
</div>
<div class="container">
    <form action="" method="post" class="block-center">
        
        <div class="form-group col-6">
            <label for="email">Email</label>
            <input type="text" name="email" class="form-control" id="email">
        </div>
        
        <div class="form-group col-6">
            <label for="password">Password</label>
            <input type="text" name="password" class="form-control" id="password">
        </div>

        <div class="form-group col-6">
            <input type="submit" class="btn btn-success btn-block" id="submit" name="sign_in" value="Sign in">
        </div>

    </form>
</div>
<%@include file="template/footer.jsp" %>