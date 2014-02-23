package com.bmw.can2udpviewer;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.hacktheride.ecowallet.R;

public class EcoPointsAdaptor extends ArrayAdapter<PointsTransaction> {

	private Context mAppContext;
	private ArrayList<PointsTransaction> mTransactionList = new ArrayList<PointsTransaction>();
	
	public EcoPointsAdaptor(Context context, int resource,
			ArrayList<PointsTransaction> transactionList) {
		super(context, resource, transactionList);
		mAppContext = context;
		mTransactionList.addAll(transactionList);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View item = convertView; // Elemento a dibujar
		ViewHolder holder; // Objeto que contiene lo que hay que dibujar

		// Si el item que hay que dibujar esta vacio, se crea uno nuevo
		if (item == null) {
			LayoutInflater inflater = (LayoutInflater) mAppContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			item = inflater.inflate(R.layout.layout_transaction, null);

			// Creamos el holder que contendra la informacion a mostrar
			holder = new ViewHolder(item);

			// Le asociamos al view que se quiere dibujar el holder creado
			item.setTag(holder);

		} else {
			holder = (ViewHolder) item.getTag();
		}
		holder.tvAmount.setText(Integer.toString(mTransactionList.get(position).getAmount()));
		if(mTransactionList.get(position).getAmount() > 0){
			holder.tvAmount.setTextColor(mAppContext.getResources().getColor(android.R.color.holo_green_light));
		}else if(mTransactionList.get(position).getAmount() < 0){
			holder.tvAmount.setTextColor(mAppContext.getResources().getColor(android.R.color.holo_red_light));
		}else{
			holder.tvAmount.setTextColor(mAppContext.getResources().getColor(android.R.color.black));
		}
		holder.tvOrigin.setText(mTransactionList.get(position).getShop());
		holder.tvDate.setText(mTransactionList.get(position).getDate());
		
		return item;
	}

	static class ViewHolder {
		public ViewHolder(View item){
			tvDate = (TextView) item.findViewById(R.id.textViewDate);
			tvOrigin = (TextView) item.findViewById(R.id.textViewOrigin);
			tvAmount = (TextView) item.findViewById(R.id.textViewAmount);
		}
		TextView tvDate;
		TextView tvOrigin;
		TextView tvAmount;
	}
}
