package be.thomasmore.flinkspreken;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class AccountAdapter extends ArrayAdapter<Account> {
    private final Context context;
    private final List<Account> values;
    private AdapterInterface listener;

    public AccountAdapter(Context context, List<Account> values, AdapterInterface listener) {
        super(context, R.layout.accountlistviewitem, values);
        this.context = context;
        this.values = values;
        this.listener = listener;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder = new Holder();

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.accountlistviewitem, null);

        holder.naam = (TextView) convertView.findViewById(R.id.account_naam);
        holder.deleteImage = (ImageView) convertView.findViewById(R.id.delete_icon);
        holder.deleteImage.setTag(values.get(position).getId());

        holder.deleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView imageViewDelete = (ImageView) v;
                long id = Integer.parseInt(imageViewDelete.getTag().toString());
                listener.deleteAccount(id);
            }
        });

        holder.naam.setText(values.get(position).getNaam());
        holder.deleteImage.setImageResource(getContext().getResources().getIdentifier("delete_icon", "drawable", getContext().getPackageName()));
        return convertView;
    }

    public static class Holder {
        Holder holder;
        TextView naam;
        ImageView deleteImage;
    }
}
