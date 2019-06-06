package com.code_save_the_queen.ps6;

class Post {
    private long id;
    boolean invalidate;
    boolean delay;



    public Post(long id, boolean invalidate , boolean delay) {
        this.id = id;
        this.invalidate = invalidate;
        this.delay = delay;
    }

    public long getId() {
        return id;
    }

    public boolean isInvalidate() {
        return invalidate;
    }

    public boolean isDelay() {
        return delay;
    }

   }


