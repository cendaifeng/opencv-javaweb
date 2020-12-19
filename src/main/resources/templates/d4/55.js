var sbtn = document.getElementById("sbtn");
function cc()
{    if(sbtn.onclick)
	{var myselect=document.getElementById("text");
     var index=myselect.selectedIndex;
     var myselect1=document.getElementById("text1");
     var py=myselect1.value;
     var xz=myselect.options[index].value;
      var myselect2=document.getElementById("text2");
     var index1=myselect2.selectedIndex;
     var sf=myselect2.options[index1].value;;
      var myselect3=document.getElementById("text3");
     var index2=myselect3.selectedIndex;
     var dbd=myselect3.options[index2].value;;
      var myselect4=document.getElementById("text4");
     var index3=myselect4.selectedIndex;
     var ld=myselect4.options[index3].value;;
      var myselect5=document.getElementById("text5");
     var index4=myselect5.selectedIndex;
     var bgd=myselect5.options[index4].value;;
     var myselect6=document.getElementById("text6");
     var tx=myselect6.value;
     var myselect7=document.getElementById("text7");
     var sc=myselect7.value;
     var myselect8=document.getElementById("text8");
     var kbys=myselect8.value;
     var myselect9=document.getElementById("text9");
     var index5=myselect9.selectedIndex;
     var gs=myselect9.options[index5].value;
     var myselect10=document.getElementById("text10");
     var index6=myselect10.selectedIndex;
     var zz=myselect10.options[index6].value;
     var myselect11=document.getElementById("text11");
     var index7=myselect11.selectedIndex;
     var jz=myselect11.options[index7].value;
     
     
     
     var mymap=new Map();
      if(xz!="旋转角度")
      {
      	mymap['旋转']=xz;
      }
      if(py!="")
      {
      	mymap['平移']=py;
      }
     if(sf!="缩放比例")
      {
      	mymap['缩放']=sf;
      }
      if(dbd!="level")
      {
      	mymap['对比度']=dbd;
      }
      if(ld!="level")
      {
      	mymap['亮度']=ld;
      }
      if(bgd!="level")
      {
      	mymap['对比度']=bgd;
      }
     if(tx!="")
      {
      	mymap['图像高低阈值']=tx;
      }
      if(sc!="")
      {
      	mymap['色彩抖动']=sc;
      }
      if(kbys!="")
      {
      	mymap['开闭运算']=kbys;
      }
      
      if(gs!="核大小kernelSize")
      {
      	mymap['高斯滤波']=gs;
      }
      if(zz!="核大小kernelSize")
      {
      	mymap['中指滤波']=zz;
      }
      if(jz!="核大小kernelSize")
      {
      	mymap['均值滤波']=jz;
      }
      for(var key in mymap){
      	console.log(key + ':' + mymap[key]);
      }
  }
    for(var key in mymap){
      	var ajaxdata={};
      	ajaxdata+={key : mymap[key]}+",";
      }
	$.ajax({
     //后台接收数据的路径
     type:'post',
     url:"/upload",
      data: JSON.stringify(ajaxdata),
      async: false,  
      contentType: 'application/json',// 设置为同步执行
      data: { userName: "zhang", passWord: "123"} ,
            processData : false,  // 禁止去处理发送的数据，对data参数进行序列化处理时须设置
            contentType : false,  // 禁止去设置Content-Type请求头
            success: function (res) {
                alert(res);
            },
            error: function (e) {
            }
        })
    

}


