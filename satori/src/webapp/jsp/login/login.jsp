<%@page import="com.daurenzg.satori.webapp.SatoriConstants"%>
<%@ page language="java"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<html>
  <head>
    <meta charset="utf-8">
    <title>Sign in &middot; Satori webapp</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <!-- Le styles -->
    <style type="text/css" media="all">
		@import url("layouts/base/css/bootstrap.css");
	</style>
    <style type="text/css">
      body {
        padding-top: 40px;
        padding-bottom: 40px;
        background-color: #f5f5f5;
      }

      .form-signin {
        max-width: 300px;
        padding: 19px 29px 29px;
        margin: 0 auto 20px;
        background-color: #fff;
        border: 1px solid #e5e5e5;
        -webkit-border-radius: 5px;
           -moz-border-radius: 5px;
                border-radius: 5px;
        -webkit-box-shadow: 0 1px 2px rgba(0,0,0,.05);
           -moz-box-shadow: 0 1px 2px rgba(0,0,0,.05);
                box-shadow: 0 1px 2px rgba(0,0,0,.05);
      }
      .form-signin .form-signin-heading,
      .form-signin .checkbox {
        margin-bottom: 10px;
      }
      .form-signin input[type="text"],
      .form-signin input[type="password"] {
        font-size: 16px;
        height: auto;
        margin-bottom: 15px;
        padding: 7px 9px;
      }
      
      .content-errors {
		color: red;
		font-family: sans-serif, tahoma, arial, verdana, helvetica;
		font-size: small;
		text-align: left
	}

    </style>
    
    <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
      <script src="../assets/js/html5shiv.js"></script>
    <![endif]-->

    <!-- Fav and touch icons -->
   
  </head>
<%
	String errMesageKey = (String) request.getAttribute(SatoriConstants.ERROR_MSG_KEY_ATTR);
%>
  <body>

    <div class="container">

      <form class="form-signin" action="login.html" method="post">
        <h2 class="form-signin-heading">Please sign in</h2>
        <input type="text" class="input-block-level" placeholder="Login" name="login">
        <input type="password" class="input-block-level" placeholder="Password" name="password">
        <button class="btn btn-large btn-primary" type="submit">Sign in</button>
        
        <div class="control-group error">
	        <div class="controls">
		        <span class="help-inline">
					<html:errors property="login.error.empty-fields"/>
				</span>
				<span class="help-inline">
					<%if (errMesageKey != null){%>
						<bean:message key="<%=errMesageKey%>"/>
					<%}%>
					<html:errors property="error.no.such.login"/>
				</span>
				<span class="help-inline">
					<html:errors property="error.wrong-password.message"/>
				</span>
			</div>
		</div>
      </form>
	
    </div> <!-- /container -->


  </body>
</html>
