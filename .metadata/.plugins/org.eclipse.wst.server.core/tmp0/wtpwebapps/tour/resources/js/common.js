// deletePanel 플러그인
$.fn.deletePanel = function(opt) {
	var self = this;
	var templ = `
	<form class="my-5">
	비밀번호 :
	<input type="password" name="password" required>
	<button type="submit" class="btn btn-primary btn-sm">
	<i class="fas fa-times"></i> 삭제
	</button>
	<button type="button" class="btn btn-primary btn-sm cancel">
	<i class="fas fa-undo"></i> 취소
	</button>
	</form>`;

	self.append(templ);
	var password = self.find(':password');

	// 이벤트 핸들러 등록
	$(opt.triger).click(()=>self.show()); 

	self.on('click', '.cancel', ()=>{ 
		password.val('');
		self.hide(); 
	}) 
	
	self.on('submit', 'form', function(e) { 
		e.preventDefault(); 
		if(!confirm("삭제할까요?")) 
			return;
		
		axios.delete(opt.url + '?password=' + password.val()) 
		.then(function(obj) { 
			if(obj.data.result == 'success') {
				alert(obj.data.result + ' 삭제 완료하였습니다.');
				location = opt.moveUrl; 
			} else { 
				alert(obj.data.result + ' 관리자 비밀번호를 다시 확인하세요.'); 
			}
		}).catch(console.log);
	});
	
	var url;
	triger.click(function() {
		var userId = $(this).data('user-id');
		
		if(userId)
			url = opt.url.replace('\{user-id\}', userId);
		else {
			if($(this).data('mode') == 'multiple') {
				var items = [];
				$(opt.multiple).each(function(){
					items.push($(this).val());
				});
			} else {
				url = opt.url;
			}
		}
		
		self.show();
	});
}