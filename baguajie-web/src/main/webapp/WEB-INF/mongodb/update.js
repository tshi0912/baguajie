// Fix for comment collection, make all of records with status 'VALID'
db.comment.find().forEach(function(data){
	db.comment.update({_id:data._id},{$set:{status:'VALID'}});
});