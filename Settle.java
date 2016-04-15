
public class Settle {
	
	final int maxn=1010;
	int [][] board=new int [3][3];
	int bound;
	int [][] dir={{-1,0},{0,-1},{0,1},{1,0}};
	int [][] goal_state={{0,0},{0,1},{0,2},{1,0},{1,1},{1,2},{2,0},{2,1},{2,2}};
	
	int [] mov=new int [maxn];
	int [] sol=new int [maxn];
	boolean ans;
	
	public Settle(int[] val)
	{
		int cnt=0,sx=-1,sy=-1;
		for(int i=0;i<3;i++)
		{
			for(int j=0;j<3;j++)
			{
				if(val[cnt]==9)
				{
					sx=i;
					sy=j;
				}
				board[i][j]=val[cnt];
				cnt++;
			}
		}
		
		solve(sx,sy);
	}
	
	void solve(int sx,int sy)
	{
		ans=false;
		while(!ans&&bound<=100)
		{
			bound=gao(sx,sy,0,-10);
		}
	}
	
	int h()
	{
		int res=0,curr;
		for(int i=0;i<3;i++)
			for(int j=0;j<3;j++)
			{
				curr=board[i][j];
				if(curr==9)
					continue;
				res+=Math.abs(i-goal_state[curr-1][0])+Math.abs(j-goal_state[curr-1][1]);
			}
		
		return res;
	}
	
	int gao(int x,int y,int dv,int premov)
	{
		int hv=h();
		
		if(dv+hv>bound)
			return dv+hv;
		
		if(hv==0)
		{
			ans=true;
			return dv;
		}
			
		int next_bound=Integer.MAX_VALUE;
		
		for(int i=0;i<4;i++)
		{
			if(i+premov == 3)
				continue;
			
			int xx=x+dir[i][0];
			int yy=y+dir[i][1];
			
			if(xx>=0&&xx<3&&yy>=0&&yy<3)
			{
				mov[dv]=i;
				sol[dv]=board[xx][yy];
				int tmp=board[x][y];
				board[x][y]=board[xx][yy];
				board[xx][yy]=tmp;
				
				int new_bound=gao(xx,yy,dv+1,i);
				if(ans)
					return new_bound;
				
				next_bound=Math.min(next_bound, new_bound);
				
				tmp=board[x][y];
				board[x][y]=board[xx][yy];
				board[xx][yy]=tmp;
			}
		}
		
		return next_bound;
	}
}
