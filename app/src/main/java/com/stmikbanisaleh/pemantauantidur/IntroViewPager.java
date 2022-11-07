package com.stmikbanisaleh.pemantauantidur;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class IntroViewPager extends RecyclerView.Adapter<IntroViewPager.PagerHolder>{
    Context context;
    List<ScreenItem> mListScreen;


    public IntroViewPager(Context context, List<ScreenItem> mListScreen) {
        this.context = context;
        this.mListScreen = mListScreen;
    }

    @NonNull
    @Override
    public PagerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View layoutScreen = inflater.inflate(R.layout.layout_screen, null);
//
//        ImageView imageSlide = layoutScreen.findViewById(R.id.intro_image);
//        TextView title = layoutScreen.findViewById(R.id.intro_title);
//        TextView decription = layoutScreen.findViewById(R.id.intro_description);
//
//        title.setText(mListScreen.get(position).getTitle());
//        decription.setText(mListScreen.get(position).getDescription());
//        imageSlide.setImageResource(mListScreen.get(position).getScreenImg());
//
//        parent.addView(layoutScreen);
//        return layoutScreen;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_screen, parent, false);
        IntroViewPager.PagerHolder holder =new IntroViewPager.PagerHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull PagerHolder holder, int position) {
        final ScreenItem list = mListScreen.get(position);
        holder.imageSlide.setImageResource(list.getScreenImg());
        holder.title.setText(list.getTitle());
        holder.description.setText(list.getDescription());
    }

    @Override
    public int getItemCount() {
        return mListScreen.size();
    }

public static class PagerHolder extends RecyclerView.ViewHolder {
    ImageView imageSlide;
    TextView title, description;


    public PagerHolder(@NonNull View itemView) {
        super(itemView);
        imageSlide = itemView.findViewById(R.id.intro_image);
        title = itemView.findViewById(R.id.intro_title);
        description = itemView.findViewById(R.id.intro_description);
    }


//    @NonNull
//    @Override
//    public Object instantiateItem(@NonNull ViewGroup container, int position) {
//
//
//        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View layoutScreen = inflater.inflate(R.layout.layout_screen, null);
//
//        ImageView imageSlide = layoutScreen.findViewById(R.id.intro_image);
//        TextView title = layoutScreen.findViewById(R.id.intro_title);
//        TextView decription = layoutScreen.findViewById(R.id.intro_description);
//
//        title.setText(mListScreen.get(position).getTitle());
//        decription.setText(mListScreen.get(position).getDescription());
//        imageSlide.setImageResource(mListScreen.get(position).getScreenImg());
//
//        container.addView(layoutScreen);
//        return layoutScreen;
//
//
//    }
//
//    @Override
//    public int getCount() {
//        return mListScreen.size();
//    }
//
//    @Override
//    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//        container.removeView((View) object);
//    }
//
//    @Override
//    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
//        return view == object;
//
//
  }
}
