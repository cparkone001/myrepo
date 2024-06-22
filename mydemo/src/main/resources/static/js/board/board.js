/**
 * form객체 -> json object로 변경
 */
$.fn.serializeObject = function () {
	"use strict";

	var result = {};
	var extend = function (i, element) {
		var node = result[element.name];

		if ('undefined' !== typeof node && node !== null) {
			if ($.isArray(node)) {
				node.push(element.value);
			} else {
				result[element.name] = [node, element.value];
			}
		} else {
			result[element.name] = element.value;
		}
	};

	$.each(this.serializeArray(), extend);
	return result;
};
 
 $(document).ready(function() { 
	 
	 /**
	  * 리스트에서 Tr 클릭할때
	  */
	 $("#boardTable").on("click", "tr", function (evt) {
		//debugger;
         var currentRow=$(this); 
         var tdVal =currentRow.find("td:eq(1)").html(); // get current row 1st table cell TD value		
		console.log("tdVal==>",tdVal);
		//getBoardInfo(tdVal);
		self.location ="/board/getBoardContent?&id="+ tdVal;
	 });

	/**
	 * 신규글 추가 버튼
	 */
	 $("#new_post_btn").on("click", function (evt) {
		self.location ="/board/newBoardContent";		
	 });

	/**
	 * 목록조회
	 */
	 $("#post_list_btn").on("click", function (evt) {
		self.location ="/board/";		
	 });
	/**
	 * 저장버튼 
	 */	
	 $("#save_post_btn").on("click", function (evt) {
		saveBoardInfo();
	 });
	/**
	 * 답글버튼 
	 */	
	 $("#reply_post_btn").on("click", function (evt) {
		replyBoardInfo();
	 });
});

function saveBoardInfo() {

	let jsonObj = {};
	
	jsonObj.titleNm       	=		$("#titleNm").val();
	jsonObj.userNm        	=		$("#userNm").val();
	jsonObj.contentsCnt   	=		$("#contentsCnt").val();
//	jsonObj.depthNum      	=		$("#depthNum").val();
//	jsonObj.postDate      	=		$("#postDate").val();
//	jsonObj.refSecnum     	=		$("#refSecnum").val() 
//	jsonObj.refOrderNum   	=		$("#refOrderNum").val();
	
	//boardForm
	
	$.ajax({
		type: "POST",
		url : "/board/saveBoardInfo",
		data: JSON.stringify(jsonObj),
		contentType : 'application/json;charset=UTF-8',
		dataType:"json"}) 
		.done(function(data, textStatus, xhr) {
			//const list = data.boardList;
			console.log(data);
			self.location ="/board/";
		})
		.fail(function(xhr, textStatus, errorThrown) { })
		.always(function(datar, textStatus, xhr) {
		})
		.then(function(data, textStatus, xhr) {
		});
	
}

function getBoardInfo(pId) {
//	let jsonObj = {};
//	jsonObj.prjCd = $("#kdb_prj_prjCd").val();
	$.ajax({
		type: "GET",
		url : "/board/getBoardInfo?&id="+ pId ,
		//data: JSON.stringify(jsonObj),
		contentType : 'application/json;charset=UTF-8',
		dataType:"json"}) 
		.done(function(data, textStatus, xhr) {
			//const list = data.boardList;
			console.log(data.boardList);
			$("#secnum").val(data.boardList[0].secnum);
			$("#titleNm").val(data.boardList[0].titleNm);
			$("#userNm").val(data.boardList[0].userNm);
			$("#contentsCnt").val(data.boardList[0].contentsCnt);
			$("#depthNum").val(data.boardList[0].depthNum);
			$("#postDate").val(data.boardList[0].postDate);
			$("#refSecnum").val(data.boardList[0].refSecnum);
			$("#refOrderNum").val(data.boardList[0].refOrderNum);
			
		})
		.fail(function(xhr, textStatus, errorThrown) { })
		.always(function(datar, textStatus, xhr) {
		})
		.then(function(data, textStatus, xhr) {
		});
}

function replyBoardInfo() {
//    const formParmJson = $("#myBoardForm").serializeObject();
//    console.log(formParmJson);
    $("#myBoardForm").attr("action","/board/replyBoardInfo").submit();
}