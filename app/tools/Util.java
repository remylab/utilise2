package tools;

import java.util.List;

import models.BlogPost;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Page;
import com.avaje.ebean.PagingList;

public class Util {
	
	public static class BlogResult {
		public List<BlogPost> posts;
		public int page;
		public int total;
		public int paginationFirst;
		public int paginationLast;
		
		public BlogResult(List<BlogPost> posts, int page, int total, int first, int last) {
			this.posts = posts;
			this.page = page;
			this.total = total;
			this.paginationFirst = first;
			this.paginationLast = last;
		}
	}
	
	public static BlogResult getBlogResult(int pageSize, int numPage) {
		return getBlogResult(pageSize,numPage,true);
	}
    public static BlogResult getBlogResult(int pageSize, int numPage, boolean withOffline) {
    	
        PagingList<BlogPost> pagingList = null;
        if ( withOffline ) {
        	pagingList = Ebean.find(BlogPost.class).orderBy("datePub desc").findPagingList(pageSize);
        } else {
        	pagingList = Ebean.find(BlogPost.class).where().eq("isOnline", true).orderBy("datePub desc").findPagingList(pageSize);
        }
        if ( pagingList != null) {
            pagingList.getFutureRowCount();
            
            Page<BlogPost> page = pagingList.getPage(numPage-1);
            int totalPages = page.getTotalPageCount(); 
            int currentPage = page.getPageIndex()+1;
            
            int first = Math.max(1, currentPage-3);
            int last = Math.min(totalPages, currentPage+3);
            
            return new BlogResult(page.getList(), currentPage,totalPages,first,last);
        }
        return null;
    }
    
}
