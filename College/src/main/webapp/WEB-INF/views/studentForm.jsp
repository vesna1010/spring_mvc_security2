<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib  prefix="sf" uri="http://www.springframework.org/tags/form" %>

<div class="row">
<div class="col-sm-12">

<c:if test="${student==null }"><h2 class="text-center text-danger">Not Found</h2></c:if>

<c:if test="${student!=null }">
<c:url value='/students/save?${_csrf.parameterName}=${_csrf.token}' context='/College' var="action"/>

<div class="row">
<h3 class="text-center">${message}&nbsp;Student</h3><br>

<sf:form action="${action}" method="post" 
class="form-horizontal" modelAttribute="student" enctype="multipart/form-data">

<div class="form-group">
<sf:label path="id" class="control-label col-sm-3 col-sm-offset-2">ID</sf:label>
<div class="col-sm-4">
<c:if test="${message=='Update'}">
<sf:hidden path="id"/>
</c:if>
<sf:input path="id" class="form-control" disabled="${message=='Update'}"/>
<sf:errors class="text-danger" path="id" />
</div>
</div>

<div class="form-group">
<sf:label path="dateOfEntry" class="control-label col-sm-3 col-sm-offset-2">DATE OF ENTRY</sf:label>
<div class="col-sm-4">
<div class="input-group">
<sf:input path="dateOfEntry" class="form-control datepicker" readonly="true"/>
<span class="input-group-addon showDate">
<span class="glyphicon glyphicon-calendar"></span>
</span>
</div>
<sf:errors class="text-danger" path="dateOfEntry" />
</div>
</div>

<div class="form-group">
<sf:label path="yearOfStudy" class="control-label col-sm-3 col-sm-offset-2">YEAR OF STRUDY</sf:label>
<div class="col-sm-4">
<sf:input path="yearOfStudy" class="form-control" />
<sf:errors class="text-danger" path="yearOfStudy" />
</div>
</div>

<div class="form-group">
<sf:label path="studyProgram" class="control-label col-sm-3 col-sm-offset-2">
STUDY PROGRAM</sf:label>
<div class="col-sm-4">
<sf:select path="studyProgram" class="form-control">
<sf:option value=""></sf:option>
<sf:options items="${studyPrograms}" itemValue="id" itemLabel="title" />
</sf:select>
<sf:errors class="text-danger" path="studyProgram" />
</div>
</div>

<div class="form-group">
<sf:label path="firstName" class="control-label col-sm-3 col-sm-offset-2">FIRST NAME</sf:label>
<div class="col-sm-4">
<sf:input path="firstName" class="form-control"/>
<sf:errors class="text-danger" path="firstName" />
</div>
</div>

<div class="form-group">
<sf:label path="lastName" class="control-label col-sm-3 col-sm-offset-2">LAST NAME</sf:label>
<div class="col-sm-4">
<sf:input path="lastName" class="form-control"/>
<sf:errors class="text-danger" path="lastName" />
</div>
</div>

<div class="form-group">
<sf:label path="fatherName" class="control-label col-sm-3 col-sm-offset-2">FATHER NAME</sf:label>
<div class="col-sm-4">
<sf:input path="fatherName" class="form-control"/>
<sf:errors class="text-danger" path="fatherName" />
</div>
</div>

<div class="form-group">
<sf:label path="gender" class="control-label col-sm-3 col-sm-offset-2">
GENDER</sf:label>
<div class="col-sm-4">
<sf:select path="gender" class="form-control">
<sf:option value=""></sf:option>
<sf:option value="MALE">MALE</sf:option>
<sf:option value="FEMALE">FEMALE</sf:option>
</sf:select>
<sf:errors class="text-danger" path="gender" />
</div>
</div>

<div class="form-group">
<sf:label path="dateOfBirth" class="control-label col-sm-3 col-sm-offset-2">DATE OF BIRTH</sf:label>
<div class="col-sm-4">
<div class="input-group">
<sf:input path="dateOfBirth" class="form-control datepicker" readonly="true"/>
<span class="input-group-addon showDate">
<span class="glyphicon glyphicon-calendar" ></span>
</span>
</div>
<sf:errors class="text-danger" path="dateOfBirth" />
</div>
</div>

<div class="form-group">
<sf:label path="email" class="control-label col-sm-3 col-sm-offset-2">EMAIL</sf:label>
<div class="col-sm-4">
<sf:input path="email" class="form-control"/>
<sf:errors class="text-danger" path="email" />
</div>
</div>

<div class="form-group">
<sf:label path="telephone" class="control-label col-sm-3 col-sm-offset-2">TELEPHONE</sf:label>
<div class="col-sm-4">
<sf:input path="telephone" class="form-control" />
<sf:errors class="text-danger" path="telephone" />
</div>
</div>

<div class="form-group">
<sf:label path="address" class="control-label col-sm-3 col-sm-offset-2">ADDRESS (city-street-state)</sf:label>
<div class="col-sm-4">
<sf:input path="address" class="form-control"/>
<sf:errors class="text-danger" path="address" />
<sf:errors class="text-danger" path="address.*" />
</div>
</div>

<div class="form-group">
<sf:label path="image" class="control-label col-sm-3 col-sm-offset-2">IMAGE</sf:label>
<div class="col-sm-4">
<sf:input type="file" path="file"   class="form-control" />
<sf:errors class="text-danger" path="image" />
<c:if test="${student.image!=null }">
<sf:hidden path="image"/>
</c:if>
</div>
</div>

<div class="form-group">
<sf:label path="dateOfCompletion" class="control-label col-sm-3 col-sm-offset-2">DATE OF COMPLETION</sf:label>
<div class="col-sm-4">
<div class="input-group">
<sf:input path="dateOfCompletion" class="form-control datepicker"/>
<span class="input-group-addon showDate">
<span class="glyphicon glyphicon-calendar" ></span>
</span>
</div>
<sf:errors class="text-danger" path="dateOfCompletion" />
</div>
</div>

<div class="form-group">
    <div class="col-sm-offset-5 col-sm-5">
     <div class="btn-group">
       <button type="submit" name="button" value="save" class="btn btn-primary">Save</button>
      <button type="submit" name="button" value="reset" class="btn btn-default">Reset</button>
     </div>
    </div>
  </div>

</sf:form>
</div>
</c:if>

</div>
</div>