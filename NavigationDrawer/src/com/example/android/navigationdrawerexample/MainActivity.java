/*
 * Copyright 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.navigationdrawerexample;

import java.util.Locale;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.SearchManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity
{
	private DrawerLayout mDrawerLayout; // activity��������
	private ListView mLeftDrawerList; // ����ͼ

	private CharSequence mTitle; // title
	private String[] mPlanetTitles;
	private LinearLayout mRightLayout; // �һ���ͼ

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mPlanetTitles = getResources().getStringArray(R.array.planets_array);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mLeftDrawerList = (ListView) findViewById(R.id.left_drawer);

		// set a custom shadow that overlays the main content when the drawer
		// opens
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);
		// set up the drawer's list view with items and click listener
		mLeftDrawerList.setAdapter(new ArrayAdapter<String>(this,
				R.layout.drawer_list_item, mPlanetTitles));
		mLeftDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		mRightLayout = (LinearLayout) findViewById(R.id.right_drawer);
		initActionBar();

		// ActionBarDrawerToggle ties together the the proper interactions
		// between the sliding drawer and the action bar app icon
		if (savedInstanceState == null)
		{
			selectItem(0);
		}
	}

	/**
	 * ��ʼ���Զ���actionbar����
	 */
	private void initActionBar()
	{
		getActionBar().setDisplayShowCustomEnabled(true);
		View actionbarLayout = LayoutInflater.from(this).inflate(
				R.layout.actionbar_layout, null);
		getActionBar().setCustomView(actionbarLayout);
		ImageButton leftbtn = (ImageButton) actionbarLayout
				.findViewById(R.id.left_imbt); // ��߰�ť
		ImageButton Rightbtn = (ImageButton) actionbarLayout
				.findViewById(R.id.right_imbt); // �ұ߰�ť
		leftbtn.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// ����ұ���ͼ��ʾ��رգ���������ͼ��ʾ��ر����ر�����ʾ
				if (mDrawerLayout.isDrawerOpen(mRightLayout))
				{
					mDrawerLayout.closeDrawer(Gravity.END);
				}
				if (mDrawerLayout.isDrawerOpen(mLeftDrawerList))
				{
					mDrawerLayout.closeDrawer(Gravity.START);
				} else
				{
					mDrawerLayout.openDrawer(Gravity.START);

				}
			}
		});
		Rightbtn.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// ��������ͼ��ʾ��رգ�����ұ���ͼ��ʾ��ر����ر�����ʾ
				if (mDrawerLayout.isDrawerOpen(mLeftDrawerList))
				{
					mDrawerLayout.closeDrawer(Gravity.START);
				}
				if (mDrawerLayout.isDrawerOpen(mRightLayout))
				{
					mDrawerLayout.closeDrawer(Gravity.END);
				} else
				{
					mDrawerLayout.openDrawer(Gravity.END);

				}

				// TODO Auto-generated method stub

			}
		});
	}

	/* The click listner for ListView in the navigation drawer */
	private class DrawerItemClickListener implements
			ListView.OnItemClickListener
	{
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id)
		{
			selectItem(position);
		}
	}

	// ��߻�����ͼѡ��item
	private void selectItem(int position)
	{
		// update the main content by replacing fragments
		Fragment fragment = new PlanetFragment();
		Bundle args = new Bundle();
		args.putInt(PlanetFragment.ARG_PLANET_NUMBER, position);
		fragment.setArguments(args);

		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction()
				.replace(R.id.content_frame, fragment).commit();

		// update selected item and title, then close the drawer
		mLeftDrawerList.setItemChecked(position, true);
		setTitle(mPlanetTitles[position]);
		mDrawerLayout.closeDrawer(mLeftDrawerList);
	}

	@Override
	public void setTitle(CharSequence title)
	{
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	/**
	 * fragment�滻�м���ͼ��ʾ
	 */
	public static class PlanetFragment extends Fragment
	{
		public static final String ARG_PLANET_NUMBER = "planet_number";

		public PlanetFragment()
		{
			// Empty constructor required for fragment subclasses
		}
		@Override
		public void onCreate(Bundle savedInstanceState)
		{
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
		}
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState)
		{
			View rootView = inflater.inflate(R.layout.fragment_planet,
					container, false);
			int i = getArguments().getInt(ARG_PLANET_NUMBER);
			String planet = getResources()
					.getStringArray(R.array.planets_array)[i];

			int imageId = getResources().getIdentifier(
					planet.toLowerCase(Locale.getDefault()), "drawable",
					getActivity().getPackageName());
			// ((ImageView)
			// rootView.findViewById(R.id.image)).setImageResource(imageId);
			((TextView) rootView.findViewById(R.id.textview)).setText("ѡ���"
					+ (i + 1) + "�˵�");
			getActivity().setTitle(planet);
			return rootView;
		}
	}
}