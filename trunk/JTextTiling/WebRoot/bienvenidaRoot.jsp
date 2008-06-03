<%@ include file="./cabecera.jsp" %>
	 <title>Servicio web para el uso del algoritmo JTextTiling - Servicios de Root</title>
	 <%
	 	Usuario root = (Usuario)request.getSession().getAttribute("usuarioActual");
	 	FacadeBD facadeBD = new FacadeBD();
	 	boolean esRoot = facadeBD.esRoot(root);
	 	request.setAttribute("esRoot", esRoot);
	  %>
	  
	  <c:choose>
	  	<c:when test="${esRoot}">
	  	<%request.getSession().setAttribute("botonSalir",true); %>
	  		<div class="table">
				<table>
				<tr>
					<td class="actual"><a>Estad�sticas de Usuarios</a></td>
					<td><a href="./listaUsuariosRoot.jsp">Lista de usuarios</a></td>
					<td><a href="./listaArchivosRoot.jsp">Lista de archivos</a></td>
					<td><a href="./eliminarUsuariosRoot.jsp">Eliminar Usuarios</a><br /></td>
					<td><a href="./eliminarArchivosRoot.jsp">Eliminar archivos</a></td>
				</tr>
			</table>
			</div>
			
			<%
				EstadisticasUsuarios estU = new EstadisticasUsuarios();
				EstadisticasArchivos estA = new EstadisticasArchivos();
				request.setAttribute("numArchivos",estA.getNum());
				request.setAttribute("numUsuarios",estU.getNum());
			 %>
			<div class="divPrincipalRoot">
				<table>
    			<tr>
    				<td width="450">�Hola ${sessionScope.usuarioActual}!</td>
    				<td>Una bacal� infame</td>
    			</tr>
    			<tr>
    				<td width="350">N�mero de archivos totales en el servidor: ${requestScope.numArchivos}</td>
    			</tr>
    			<tr>
    				<td width="350">N�mero de usuarios totales del servicio: ${requestScope.numUsuarios}</td>
    			</tr>
    		</table>
			</div>
	  	</c:when>
	  	<c:otherwise>
	  		<h1>QUITA COP�N, QUE NO YES ROOT!</h1>
	  	</c:otherwise>
	  </c:choose>
<%@ include file="./pie.jsp" %>