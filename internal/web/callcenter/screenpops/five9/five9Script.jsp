<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <script type="text/javascript" src="/internal/js/ZeroClipboard.js"></script>
  <style type="text/css">
      .script{
          padding: 25px;

      }
  </style>
</head>
<body>
  <div class="script">
      <h1>Copy the Text Below Into the Campaign Script for ${ccClient.mlogCampaignName}</h1>
<textarea name="box-content" id="box-content" rows="15" cols="90">
<s:property escape="true" value="five9Scrpt"/>
</textarea>
      <br>
   <p><input type="button" id="copy" name="copy" value="Copy to Clipboard" /></p>

      </div>
<script type="text/javascript">
//set path           ajax.js
ZeroClipboard.setMoviePath('/internal/js/ZeroClipboard.swf');
//create client
var clip = new ZeroClipboard.Client();
//event
clip.addEventListener('mousedown',function() {
	clip.setText(document.getElementById('box-content').value);
});
clip.addEventListener('complete',function(client,text) {
	alert('copied: ' + text);
});
//glue it to the button
clip.glue('copy');

</script>
</body>
</html>