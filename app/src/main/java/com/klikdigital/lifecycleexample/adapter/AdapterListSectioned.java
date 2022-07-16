package com.klikdigital.lifecycleexample.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.klikdigital.lifecycleexample.R;
import com.klikdigital.lifecycleexample.model.People;
import com.klikdigital.lifecycleexample.utils.Tools;

import java.util.ArrayList;
import java.util.List;

public class AdapterListSectioned extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_ITEM = 1;
    private final int VIEW_SECTION = 0;
    private final Context ctx;
    /* access modifiers changed from: private */
    public List<People> items = new ArrayList();
    /* access modifiers changed from: private */
    public OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, People people, int i);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public AdapterListSectioned(Context context, List<People> list) {
        this.items = list;
        this.ctx = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public View lyt_parent;
        public TextView name;

        public OriginalViewHolder(View view) {
            super(view);
            this.image = view.findViewById(R.id.image);
            this.name = view.findViewById(R.id.name);
            this.lyt_parent = view.findViewById(R.id.lyt_parent);
        }
    }

    public static class SectionViewHolder extends RecyclerView.ViewHolder {
        public TextView title_section;

        public SectionViewHolder(View view) {
            super(view);
            this.title_section = view.findViewById(R.id.title_section);
        }
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if (i == 1) {
            return new OriginalViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_people_chat, viewGroup, false));
        }
        return new SectionViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_section, viewGroup, false));
    }

    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int i) {
        People people = this.items.get(i);
        if (viewHolder instanceof OriginalViewHolder) {
            OriginalViewHolder originalViewHolder = (OriginalViewHolder) viewHolder;
            originalViewHolder.name.setText(people.name);
            Tools.displayImageRound(this.ctx, originalViewHolder.image, people.image);
            originalViewHolder.lyt_parent.setOnClickListener(view -> {
                if (AdapterListSectioned.this.mOnItemClickListener != null) {
                    AdapterListSectioned.this.mOnItemClickListener.onItemClick(view, AdapterListSectioned.this.items.get(i), i);
                }
            });
            return;
        }
        ((SectionViewHolder) viewHolder).title_section.setText(people.name);
    }

    public int getItemCount() {
        return this.items.size();
    }

    public int getItemViewType(int i) {
        return this.items.get(i).section ^ true ? 1 : 0;
    }

    public void insertItem(int i, People people) {
        this.items.add(i, people);
        notifyItemInserted(i);
    }

}
