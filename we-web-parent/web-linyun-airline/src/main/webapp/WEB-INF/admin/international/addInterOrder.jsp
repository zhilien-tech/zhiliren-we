<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en-US">
<head>
    <meta charset="UTF-8">
    <title>国际-添加订单</title>
	<link rel="stylesheet" href="${base}/public/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="${base}/public/dist/css/AdminLTE.css">
  <link rel="stylesheet" href="${base}/public/font-awesome/css/font-awesome.min.css">
  <link rel="stylesheet" type="text/css" href="${base}/public/ionicons/css/ionicons.min.css">
  <link rel="stylesheet" type="text/css" href="${base}/public/dist/css/inlandCross.css">
  <link rel="stylesheet" href="${base}/public/dist/css/internationOrderDetail.css"><!--本页面styleFlie-->
</head>
<body>
	<div class="modal-top">
     <div class="modal-header">
          <button type="button" class="btn btn-primary right btn-sm">取消</button>
          <button type="submit" class="btn btn-primary right btn-sm">保存</button>
          <select class="form-control input-sm orderSelect right">
            <option>查询</option>
            <option>预定</option>
            <option>出票</option>
            <option>开票</option>
            <option>关闭</option>
          </select>
          <label class="right orderLabel">生成订单</label>
          <input class="right orderInput" type="checkbox">
          <h4>添加订单</h4>
     </div>
      <div class="modal-body modal-hei">
          <div class="customerInfo"><!--客户信息-->
               <div class="infoTop">
                 <p>客户信息</p>
               </div>
               <div class="infofooter">
                 <table>
                   <tr>
                     <td><label>客户姓名：</label></td>
                     <td><input type="text" class="form-control input-sm" placeholder="请输入客户姓名"></td>
                     <td><label style="position: relative;top: 4px;">结算方式：</label></td>
                     <td colspan="3"><pre class="preTxt">不限 信用额度：0  临时额度：0  历史欠款：0  预存款：0</pre></td>
                     <td><input type="button" value="清空" class="btn btn-primary btn-sm clearBtn"><i class="UnderIcon fa fa-chevron-circle-down"></i></td>
                   </tr>
                 </table>

                 <table class="hideTable none">
                   <tr>
                     <td><label>公司简称：</label></td>
                     <td><input type="text" class="form-control input-sm" placeholder="请输入公司简称"></td>
                     <td><label>电话：</label></td>
                     <td><input type="text" class="form-control input-sm" placeholder="请输入电话"></td>
                     <td><label>地址：</label></td>
                     <td colspan="3"><input type="text" class="form-control input-sm addressInput" placeholder="请输入地址"></td>
                   </tr>
                   <tr class="KHinfo">
                     <td><label>负责人：</label></td>
                     <td><input type="text" class="form-control input-sm" placeholder="请输入负责人"></td>
                     <td><label>网址：</label></td>
                     <td><input type="text" class="form-control input-sm" placeholder="请输入网址"></td>
                     <td><label>传真：</label></td>
                     <td><input type="text" class="form-control input-sm" placeholder="请输入传真"></td>
                     <td><label>出发城市：</label></td>
                     <td><input type="text" class="form-control input-sm addressInput" placeholder="请输入出发城市"></td>
                     
                   </tr>
                 </table>

               </div>
          </div><!--end 客户信息-->
          <div class="customerInfo"><!--航段信息-->
               <div class="infoTop">
                 <p>航段信息</p>
               </div>
               <div id="infofooter" class="infofooter">
                 <table class="addHDtable">
                    <tbody>
                          <tr>
                             <td><label>航空公司：</label></td>
                             <td><input type="text" class="form-control input-sm disab"></td>
                             <td><label>人数：</label></td>
                             <td><input type="text" class="form-control input-sm disab"></td>
                             <td><label>成本单价：</label></td>
                             <td><input type="text" class="form-control input-sm disab"></td>
                           </tr>
                          <tr>
                            <td><label>记录编号：</label></td>
                            <td colspan="11"><input type="text" class="form-control input-sm numTd"></td>
                          </tr>
                          <tr class="addHD-tr">
                            <td><label>出发城市：</label></td>
                            <td><input type="text" class="form-control input-sm"></td>
                            <td><label>抵达城市：</label></td>
                            <td><input type="text" class="form-control input-sm"></td>
                            <td><label>出发日期：</label></td>
                            <td><input type="text" class="form-control input-sm" placeholder="2017-03-16"></td>
                            <td><label>航班号：</label></td>
                            <td><input type="text" class="form-control input-sm" placeholder="ca309"></td>
                            <td><label>出发时间：</label></td>
                            <td><input type="text" class="form-control input-sm" placeholder="2017-03-16"></td>
                            <td><label>抵达时间：</label></td>
                            <td><input type="text" class="form-control input-sm" placeholder="2017-03-16"></td>
                            <td><a href="javascript:;" class="glyphicon glyphicon-plus addHDIcon"></a></td>
                          </tr>
                    </tbody>
                 </table>
               </div>
          </div><!--end 航段信息-->
      </div>
	</div>
   <!--JS 文件-->
	<script src="${base}/public/plugins/jQuery/jquery-2.2.3.min.js"></script>
	<script src="${base}/public/bootstrap/js/bootstrap.min.js"></script>
	<script src="${base}/public/plugins/slimScroll/jquery.slimscroll.min.js"></script><!-- SlimScroll -->
	<script src="${base}/public/plugins/fastclick/fastclick.js"></script><!-- FastClick -->
	<script src="${base}/public/dist/js/app.min.js"></script><!-- AdminLTE App -->
  <script type="text/javascript">
    $(function(){
      $('.UnderIcon').on('click',function(){//客户信息 显示/隐藏
          $('.hideTable').toggle('400');
        });
      //客户需求的 + 按钮
      $('.addHDIcon').click(function(){
          var divTest = $(this).parent().parent(); 
          var newDiv = divTest.clone(true);
          divTest.after(newDiv);
          var No = parseInt(divTest.find("p").html())+1;//用p标签显示序号
          newDiv.find("p").html(No); 
          newDiv.find('.addHDIcon').parent().remove();
          newDiv.append('<td class="removeIconTd"><i class="glyphicon glyphicon-minus removHDIcon"></i></td>');
      });

      //客户需求的 - 按钮
      $(document).on("click",".removHDIcon",function(){
          $(this).parent().parent().remove();
      });
    });
  </script>
</body>
</html>	