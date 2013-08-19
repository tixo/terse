package org.terse.util;

import java.util.List;

/**
 * @name 分页
 * 
 */
@SuppressWarnings("unchecked")
public class Page implements java.io.Serializable{
	private static final long serialVersionUID = -575676371540979332L;
	public static int DEFAULT_OFFSET = 2;
	
	private int currentPage;
	private int maxPage;
	private int nextPage;
	private List<Integer> pageNum;
	private List list;
	private int count;
	private int prePage;
	private int pageSize = 10;
	private int	offset = DEFAULT_OFFSET;

	public Page() {
	}

	/**
	 * @param currentPage
	 *            当前
	 * @param maxPage
	 *            最大页数
	 * @param pageNum
	 *            页码数组
	 * @param resultlist
	 *            数据
	 */
	public Page(int count, int currentPage, int maxPage, List<Integer> pageNum,
			List resultlist, int nextPage, int prePage) {
		this.count = count;
		this.currentPage = currentPage;
		this.maxPage = maxPage;
		this.pageNum = pageNum;
		this.list = resultlist;
		this.nextPage = nextPage;
		this.prePage = prePage;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public void setPageNum(List<Integer> pageNum) {
		this.pageNum = pageNum;
	}

	public List<Integer> getPageNum() {
		return pageNum;
	}

	public int getMaxPage() {
		return maxPage;
	}

	public void setMaxPage(int maxPage) {
		this.maxPage = maxPage;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getNextPage() {
		return nextPage;
	}

	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}

	public int getPrePage() {
		return prePage;
	}

	public void setPrePage(int prePage) {
		this.prePage = prePage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	/**
	 * url提交方式，非form提交方式
	 * 考虑几个情况： 
	 * 1 .. 8 9 (10) 
	 * 1 .. 7 8 (9) 10
	 * 1 .. 6 7 (8) 9 10
	 * 1 .. 5 6 (7) 8 9 10
	 * 1 .. 4 5 (6) 7 8 .. 10
	 * 1 .. 3 4 (5) 6 7 .. 10
	 * 1 2 3 (4) 5 6 .. 10
	 * 1 2 (3) 4 5 .. 10
	 * 1 (2) 3 4 .. 10
	 * (1) 2 3 .. 10
	 * @param url
	 * @return
	 */
	public String getPageInfo(String url)
	{
		int firstPage, lastPage, offsetFirst, offsetLast, prevActive, nextActive ;
		@SuppressWarnings("unused")
		int prevNo,nextNo;
		
		// 数值 0 表示无效数据
		firstPage = currentPage == 1? 0: 1;
		lastPage = currentPage == maxPage? 0 : maxPage;
		prevNo = currentPage <= 1? 0: this.currentPage - 1;
		nextNo = currentPage >= maxPage? 0: currentPage + 1;
		
		offsetFirst = Math.max(this.currentPage - this.offset, 2);
		offsetLast = Math.min(this.currentPage + this.offset, this.maxPage-1);
		
		prevActive = Math.max(offsetFirst - this.offset - 1, 1);
		nextActive = Math.min(offsetLast + this.offset + 1, this.maxPage);
		
		if( this.currentPage - this.offset <= 2 )
			prevActive = 0;
		if( this.currentPage + this.offset >= this.maxPage - 1)
			nextActive = 0;
		
		StringBuilder print = new StringBuilder(1024 * 2);
		String contract = url.indexOf("?")>0? "&":"?";
		//String contract = url.indexOf("%3F")>0? "&":"?";
		url = url + contract;
		
		print.append("<span class=\"navigator\">");
		
		print.append("页码：");
		
		if( firstPage > 0 )
			addHref(print, "nav-first", url, 1);
		
		if( prevActive > 0 )
			addHref(print, "nav-prev-sect", url, prevActive, "&lt;&lt;");
		
		if( offsetFirst > 0 )
			for(int i = offsetFirst; i < currentPage; ++i)
				addHref(print, "nav-page", url, i);
		
		print.append("<span class=\"nav-current\">").append(currentPage).append("</span>");
		
		if( offsetLast > 0 )
			for(int i = currentPage + 1; i <= offsetLast; ++i)
				addHref(print, "nav-page", url, i);
		
		if( nextActive > 0 )
			addHref(print, "nav-next-sect", url, nextActive, "&gt;&gt;");
		
		if( lastPage > 0 )
			addHref(print, "nav-last", url, lastPage);

		print.append("</span>");
		return print.toString();
	}
	private void addHref(StringBuilder print, String style, String url, int pageNo)
	{
		addHref(print, style, url, pageNo, Integer.toString(pageNo));
	}

	private void addHref(StringBuilder print, String style, String url, int pageNo, String caption)
    {
		print.append("<a");
		if( style != null )
			print.append(" class=\"").append(style).append(" showprogress \"");
		print.append(" href=\"");
		print.append(url);
		print.append("pageno=");
		print.append(pageNo);
		print.append("\">");
		print.append(caption);
		print.append("</a>");
    }
	
	/**
	 * 考虑几个情况： 
	 * 1 .. 8 9 (10) 
	 * 1 .. 7 8 (9) 10
	 * 1 .. 6 7 (8) 9 10
	 * 1 .. 5 6 (7) 8 9 10
	 * 1 .. 4 5 (6) 7 8 .. 10
	 * 1 .. 3 4 (5) 6 7 .. 10
	 * 1 2 3 (4) 5 6 .. 10
	 * 1 2 (3) 4 5 .. 10
	 * 1 (2) 3 4 .. 10
	 * (1) 2 3 .. 10
	 * @param url
	 * @return
	 */
	public String getFindPageInfo(String formId)
	{
		int firstPage, lastPage, offsetFirst, offsetLast, prevActive, nextActive ;
		@SuppressWarnings("unused")
		int prevNo,nextNo;
		
		// 数值 0 表示无效数据
		firstPage = currentPage == 1? 0: 1;
		lastPage = currentPage == maxPage? 0 : maxPage;
		prevNo = currentPage <= 1? 0: this.currentPage - 1;
		nextNo = currentPage >= maxPage? 0: currentPage + 1;
		
		offsetFirst = Math.max(this.currentPage - this.offset, 2);
		offsetLast = Math.min(this.currentPage + this.offset, this.maxPage-1);
		
		prevActive = Math.max(offsetFirst - this.offset - 1, 1);
		nextActive = Math.min(offsetLast + this.offset + 1, this.maxPage);
		
		if( this.currentPage - this.offset <= 2 )
			prevActive = 0;
		if( this.currentPage + this.offset >= this.maxPage - 1)
			nextActive = 0;
		
		StringBuilder print = new StringBuilder(1024 * 2);
		
		print.append("<span class=\"navigator\">");
		
		print.append("页码：");
		
		if( firstPage > 0 )
			addFindHref(print, "nav-first", formId, 1);
		
		if( prevActive > 0 )
			addFindHref(print, "nav-prev-sect", formId, prevActive, "&lt;&lt;");
		
		if( offsetFirst > 0 )
			for(int i = offsetFirst; i < currentPage; ++i)
				addFindHref(print, "nav-page", formId, i);
		
		print.append("<span class=\"nav-current\">").append(currentPage).append("</span>");
		
		if( offsetLast > 0 )
			for(int i = currentPage + 1; i <= offsetLast; ++i)
				addFindHref(print, "nav-page", formId, i);
		
		if( nextActive > 0 )
			addFindHref(print, "nav-next-sect", formId, nextActive, "&gt;&gt;");
		
		if( lastPage > 0 )
			addFindHref(print, "nav-last", formId, lastPage);

		print.append("</span>");
		return print.toString();
	}
	
	private void addFindHref(StringBuilder print, String style, String formId, int pageNo)
	{
		addFindHref(print, style, formId, pageNo, Integer.toString(pageNo));
	}
	/**
	 * form提交方式
	 * @param print
	 * @param style
	 * @param url
	 * @param pageNo
	 * @param caption
	 */
	private void addFindHref(StringBuilder print, String style, String formId, int pageNo, String caption)
    {
		print.append("<a");
		if( style != null )
			print.append(" class=\"").append(style).append(" showprogress \"");
		print.append(" href=\"javascript:");
		if ( formId==null ||formId.trim().equals("") )
			print.append("findPage('"+pageNo+"',this)");
		else
			print.append("findPage('"+pageNo+"',this,'"+formId+"')");
		print.append("\" >");
		print.append(caption);
		print.append("</a>");
    }
}
