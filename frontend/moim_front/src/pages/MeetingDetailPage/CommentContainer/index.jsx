import React from "react";

function CommentContainer() {
  return (
    <div>
      <div>
        <textarea
          className="w-1/2 flex items-center rounded-lg h-20 text-black text-sm placeholder:text-darkgray placeholder:justify-center focus:outline-none resize-none overflow-hidden border-[#FF2440]"
          placeholder="댓글 작성하기"
          onChange={(e) => setComment(e.target.value)}
          value={comment}
        />
        <button className="text-white bg-crimson">REPLY</button>
      </div>
    </div>
  );
}

export default CommentContainer;
