package com.mani.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;


public abstract class StaggeredGridViewItem {

	public abstract View getView(int position, LayoutInflater inflater, ViewGroup parent);
	public abstract int getViewHeight(LayoutInflater inflater, ViewGroup parent);
	
}
