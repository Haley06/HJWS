package com.smallbell.modules.app.service;

import com.smallbell.common.utils.R;
import com.smallbell.modules.app.form.comment.AddCommentForm;
import com.smallbell.modules.app.form.comment.CommentIdForm;
import com.smallbell.modules.app.form.comment.RecordIdForm;

public interface CommentService {

    R addComment(AddCommentForm form,Long userId);

    R deleteComment(CommentIdForm form, Long userId);

    R getUserComment(Long userId);

    R GetByRecordId(RecordIdForm form);
}
