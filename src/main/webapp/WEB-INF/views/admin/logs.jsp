<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<tiles:insertDefinition name="d2d.dashboard">
   <tiles:putAttribute name="title">
      Logs
   </tiles:putAttribute>
   <tiles:putAttribute name="body">
      <section class="content">
         <div class="row">
            <div class="col-md-12">
               <div class="nav-tabs-custom">
                  <ul class="nav nav-tabs">
                     <li class="active"><a href="#tab_1" data-toggle="tab">Logs</a></li>
                  </ul>
                  <div class="tab-content">
                     <div class="tab-pane active" id="tab_1">
                        <p>Aqui podra descargar el ultimo log.</p>
                        <br>
                        <a href="javascript:window.location='downloadLog'">Descargar</a>
                     </div>
                  </div>
               </div>
            </div>
         </div>
      </section>
      <script></script>
   </tiles:putAttribute>
</tiles:insertDefinition>