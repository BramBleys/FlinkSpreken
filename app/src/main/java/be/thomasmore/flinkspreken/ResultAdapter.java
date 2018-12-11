package be.thomasmore.flinkspreken;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ResultAdapter extends ArrayAdapter<Score> {
    private final Context context;
    private final List<Score> values;
    private DatabaseHelper db;


    public ResultAdapter(Context context, List<Score> values) {
        super(context, R.layout.resultlistviewitem, values);
        this.context = context;
        this.values = values;
        db = new DatabaseHelper(getContext());
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.resultlistviewitem, parent, false);

        final TextView textViewPaar = (TextView) rowView.findViewById(R.id.paar_textview);
        final TextView textViewScore = (TextView) rowView.findViewById(R.id.score_textview);
        final TextView textViewSpel = (TextView) rowView.findViewById(R.id.score_spel);
        final TextView textViewDatum = (TextView) rowView.findViewById(R.id.score_datum);

        String paar_naam = db.getPaar(values.get(position).getPaarId()).getnaam();
        String score_string = values.get(position).getScore();
        String spel = values.get(position).getSpel();
        String datum = values.get(position).getDatum();

        textViewPaar.setText(paar_naam);
        textViewScore.setText(score_string);
        textViewSpel.setText(spel);
        textViewDatum.setText(datum);

        return rowView;
    }

}
