package com.maxq.fragment;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import org.xutils.x;
import org.xutils.image.ImageOptions;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.maxq.R;
import com.maxq.activity.HomePageActivity;
import com.maxq.bean.ImageBean;
import com.maxq.utils.CostomValue;
import com.utils.adapterutils.CommonAdapter;
import com.utils.adapterutils.ViewHolder;
import com.utils.widget.grid.StaggeredGridView;
import com.utils.widget.head.HeaderFragment;

public class HomePageFragment extends HeaderFragment {
		private StaggeredGridView mListView;
	    private List<ImageBean> mListViewTitles;
	    private boolean mLoaded;

	    private AsyncLoadSomething mAsyncLoadSomething;
	    private FrameLayout mContentOverlay;
//	    List<ImageBean> list=new ArrayList<ImageBean>();
	    public List<Integer> mPos=new ArrayList<Integer>();//处理checkbox错乱
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	    // TODO Auto-generated method stub
	    super.onCreate(savedInstanceState);
	    }
	    @Override
	    public void onAttach(Activity activity) {
	        super.onAttach(activity);
	        setHeaderBackgroundScrollMode(HEADER_BACKGROUND_SCROLL_PARALLAX);
	        setOnHeaderScrollChangedListener(new OnHeaderScrollChangedListener() {
	            @Override
	            public void onHeaderScrollChanged(float progress, int height, int scroll) {
	            	
	                height -= getActivity().getActionBar().getHeight();

	                progress = (float) scroll / height;
	                
	                if (progress > 1f) progress = 1f;

	                // *
	                // `*
	                // ```*
	                // ``````*
	                // ````````*
	                // `````````*
	                progress = (1 - (float) Math.cos(progress * Math.PI)) * 0.5f;

	                ((HomePageActivity) getActivity())
	                        .getFadingActionBarHelper()
	                        .setActionBarAlpha((int) (255 * progress));
	            }
	        });

	        cancelAsyncTask(mAsyncLoadSomething);
	        mAsyncLoadSomething = new AsyncLoadSomething(this);
	        mAsyncLoadSomething.execute();
	    }

	    @Override
	    public void onDetach() {
	        cancelAsyncTask(mAsyncLoadSomething);
	        super.onDetach();
	    }

	    @Override
	    public View onCreateHeaderView(LayoutInflater inflater, ViewGroup container) {
	        return inflater.inflate(R.layout.fragment_header, container, false);
	    }

	    @Override
	    public View onCreateContentView(LayoutInflater inflater, ViewGroup container) {
	        mListView = (StaggeredGridView) inflater.inflate(R.layout.homepage_list, container, false);
	        if (mLoaded) setListViewTitles(mListViewTitles);
	        lisenView();
	        return mListView;
	    }
	    private void lisenView() {
	    	mListView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					
				}
			});
		}
		@Override
	    public void onPause() {
	    // TODO Auto-generated method stub
	    super.onPause();
	    
	    }
	    @Override
	    public View onCreateContentOverlayView(LayoutInflater inflater, ViewGroup container) {
	        ProgressBar progressBar = new ProgressBar(getActivity());
	        mContentOverlay = new FrameLayout(getActivity());
	        mContentOverlay.addView(progressBar, new FrameLayout.LayoutParams(
	                ViewGroup.LayoutParams.WRAP_CONTENT,
	                ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER));
	        if (mLoaded) mContentOverlay.setVisibility(View.GONE);
	        return mContentOverlay;
	    }

	    private void setListViewTitles(final List<ImageBean> list) {
	        mLoaded = true;
	        mListViewTitles = list;
	        if (mListView == null) return;
	        mListView.setVisibility(View.VISIBLE);
	        setListViewAdapter(mListView, new CommonAdapter<ImageBean>(getActivity(),
	        		mListViewTitles,R.layout.list_index_item) {

				@Override
				public void conver(final ViewHolder holder, ImageBean t) {
//					x.image().bind((ImageView)holder.getView(R.id.list_index_item_imgs),
//							t.getUrl());
					x.image().bind((ImageView)holder.getView(R.id.list_index_item_imgs),
							t.getUrl(),new ImageOptions.Builder().setSize(120, 260).build());
					final CheckBox cb=holder.getView(R.id.list_index_item_enjoy);
					cb.setChecked(false);
					if(mPos.contains(holder.getmPosition())){
						cb.setChecked(true);
					}
					cb.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View arg0) {
							if(cb.isClickable()){
								mPos.add(holder.getmPosition());
							}else{
								mPos.remove((Integer)holder.getmPosition());
							}
						}
					});
				}
			});
//	        setListViewAdapter(mListView,new myAdapter(getActivity(), 
//	        		mListViewTitles, R.layout.list_index_item));
	    }
	    public class myAdapter extends BaseAdapter{
	    	Context context;
	    	List<ImageBean> list;
	    	int layout;
	    	public myAdapter(Context context,List<ImageBean> list,int layout){
	    		this.context=context;
	    		this.list=list;
	    		this.layout=layout;
	    	}

			@Override
			public int getCount() {
				return list.size();
			}

			@Override
			public Object getItem(int arg0) {
				return list.get(arg0);
			}

			@Override
			public long getItemId(int arg0) {
				return arg0;
			}

			@Override
			public View getView(int arg0, View arg1, ViewGroup arg2) {
				HonderView honderView;
				if(arg1==null){
					arg1=LayoutInflater.from(context).inflate(layout, null);
					honderView=new HonderView();
					honderView.myI=(ImageView) arg1.findViewById(R.id.list_index_item_imgs);
					arg1.setTag(honderView);
				}else{
					honderView=(HonderView) arg1.getTag();
				}
//				x.image().bind(honderView.myI, list.get(arg0).getUrl());
				return arg1;
			}
	    	class HonderView{
	    		private ImageView myI;
	    	}
	    }
	    
	   
		private void cancelAsyncTask(AsyncTask task) {
	        if (task != null) task.cancel(false);
	    }

	    // //////////////////////////////////////////
	    // ///////////// -- LOADER -- ///////////////
	    // //////////////////////////////////////////

	    private  class AsyncLoadSomething extends AsyncTask<Void, Void,List<ImageBean>> {

	        private static final String TAG = "AsyncLoadSomething";

	        final WeakReference<HomePageFragment> weakFragment;

	        public AsyncLoadSomething(HomePageFragment fragment) {
	            this.weakFragment = new WeakReference<HomePageFragment>(fragment);
	        }

	        @Override
	        protected void onPreExecute() {
	            super.onPreExecute();

	            final HomePageFragment audioFragment = weakFragment.get();
	            if (audioFragment.mListView != null) audioFragment.mListView.setVisibility(View.INVISIBLE);
	            if (audioFragment.mContentOverlay != null) audioFragment.mContentOverlay.setVisibility(View.VISIBLE);
	        }

	        @Override
	        protected List<ImageBean> doInBackground(Void... voids) {

//	            try {
//	                // Emulate long downloading
//	                Thread.sleep(2000);
//	            } catch (InterruptedException e) {
//	                e.printStackTrace();
//	            }
	            ArrayList<ImageBean> list=new ArrayList<ImageBean>();
	            String imgUrl="http://img30.360buyimg.com/popWareDetail/jfs/t2887/35/3271499137/813367/859aa57f/57873e9bN6d0a42a6.jpg";
	            for (int i = 0; i < 31; i++) {
	    			ImageBean bean=new ImageBean();
	    			bean.setUrl(imgUrl);
	    			bean.setLinkUrl("");
	    			list.add(bean);
	    		}

	            return list;
	        }

	        @Override
	        protected void onPostExecute(List<ImageBean>  titles) {
	            super.onPostExecute(titles);
	            final HomePageFragment audioFragment = weakFragment.get();
	            if (audioFragment == null) {
	                if (CostomValue.DEBUG) Log.d(TAG, "Skipping.., because there is no fragment anymore.");
	                return;
	            }

	            if (audioFragment.mContentOverlay != null) audioFragment.mContentOverlay.setVisibility(View.GONE);
	            audioFragment.setListViewTitles(titles);
	        }
	    }
}
