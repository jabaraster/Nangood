$(function() {
	$('.btn-danger').click(function() {
		return confirm('セッションを削除すると、関連するデータは全て削除され、二度と復元出来ません。\n削除してよろしいですか？');
	});
});
