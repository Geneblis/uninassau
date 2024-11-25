package com.blackbird.game;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private Player player;
    private Controles controles;
    private List<Bala> balas;

    @Override
    public void create() {
        batch = new SpriteBatch();
        balas = new ArrayList<>();

        //player settings
        player = new Player();
        player.changeAngle(45);
        player.definirPosicao(150, 100); //Posicao Inicial
        controles = new Controles(player, this); //vincular o player aos controles e vinular o main com this, "this" no codigo dos controles referente ao player.
    }

    @Override

    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f); // Cor de fundo
        batch.begin();
        //comeco do render

        player.update();
        player.draw(batch);
        controles.update(); // Atualiza os controles

        //array das balas
        for (int i = 0; i < balas.size(); i++) {
            Bala bala = balas.get(i);
            bala.update();
            if (!bala.check(Gdx.graphics.getWidth(), Gdx.graphics.getHeight())) {
                balas.remove(i);
                i--;
            }else {
                bala.draw(batch);
            }
        }

        batch.end();
        //finalizacao do render
    }



    public void atirar() {
        //Boca do player
        float offset = 15;
        float posX = player.getX() + (float) Math.cos(Math.toRadians(player.getAngle())) * offset;
        float posY = player.getY() + (float) Math.sin(Math.toRadians(player.getAngle())) * offset;
        float angle = player.getAngle();

        //debug pq esta porra eh bugada pra krl
        System.out.println("Atirando: Posição (" + posX + ", " + posY + ") Ângulo: " + angle);
        balas.add(new Bala(posX, posY, angle, 10, 5));
    }

    //todas as imagens para serem deletadas da RAM apos o uso.
    @Override
    public void dispose() {
        batch.dispose();
        player.dispose();
        for (Bala bala : balas) {
            bala.dispose(); // Dispose de cada bala
        }
        balas.clear();
    }
}