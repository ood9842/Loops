package com.oop.loop.mapScreen;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;

/**
 * Created by Chetsada Chaiprasop on 11/15/2016.
 */
public class Wolrd_Map {
    public static final int STRAT_MAP = 0;
    public static final int DEFAULT_MAP = 1;
    public static final int QUIZ_MAP1 = 2;

    private ArrayList<ArrayList<Rectangle>> map_reg;
    private ArrayList<OrthogonalTiledMapRenderer> map_img;
    private TmxMapLoader mapLoader;
    private TiledMap map;

    public Wolrd_Map()
    {
        mapLoader =new TmxMapLoader();
        map_reg = new ArrayList<ArrayList<Rectangle>>();
        map_img = new ArrayList<OrthogonalTiledMapRenderer>();
        for(int i = 0;i<3;i++) {
            ArrayList<Rectangle> reg = new ArrayList<Rectangle>();
            map = mapLoader.load("map_stage\\map"+(i+1)+".tmx");
            map_img.add(new OrthogonalTiledMapRenderer(map));
            for(MapObject object:map.getLayers().get("not_pass").getObjects().getByType(RectangleMapObject.class))
            {
                reg.add(((RectangleMapObject) object).getRectangle());
            }
            map_reg.add(reg);
        }
    }

    public OrthogonalTiledMapRenderer getMap(int index)
    {
        return map_img.get(index);
    }

    public ArrayList<Rectangle> getReg(int index)
    {
        return map_reg.get(index);
    }
}
