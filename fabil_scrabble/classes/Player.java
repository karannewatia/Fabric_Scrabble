import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
/**
 * This class represents a player in the Scrabble game.
 */
interface Player extends fabric.lang.Object {
    public Rack get$rack();
    
    public Rack set$rack(Rack val);
    
    public int get$score();
    
    public int set$score(int val);
    
    public int postInc$score();
    
    public int postDec$score();
    
    public Player Player$();
    
    public java.lang.String getRack();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements Player {
        public Rack get$rack() { return ((Player._Impl) fetch()).get$rack(); }
        
        public Rack set$rack(Rack val) {
            return ((Player._Impl) fetch()).set$rack(val);
        }
        
        public int get$score() { return ((Player._Impl) fetch()).get$score(); }
        
        public int set$score(int val) {
            return ((Player._Impl) fetch()).set$score(val);
        }
        
        public int postInc$score() {
            return ((Player._Impl) fetch()).postInc$score();
        }
        
        public int postDec$score() {
            return ((Player._Impl) fetch()).postDec$score();
        }
        
        public Player Player$() { return ((Player) fetch()).Player$(); }
        
        public java.lang.String getRack() {
            return ((Player) fetch()).getRack();
        }
        
        public _Proxy(Player._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.lang.Object._Impl implements Player
    {
        public Rack get$rack() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.rack;
        }
        
        public Rack set$rack(Rack val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.rack = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        /**
         * This rack represents P's rack.
         * It's fine to make the reference to the rack public, as all the
         * information contained in the board is readable only by P
         */
        public Rack rack;
        
        public int get$score() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.score;
        }
        
        public int set$score(int val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.score = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public int postInc$score() {
            int tmp = this.get$score();
            this.set$score((int) (tmp + 1));
            return tmp;
        }
        
        public int postDec$score() {
            int tmp = this.get$score();
            this.set$score((int) (tmp - 1));
            return tmp;
        }
        
        public int score;
        
        public Player Player$() {
            fabric$lang$Object$();
            this.set$rack(
                   ((Rack) new Rack._Impl(this.$getStore()).$getProxy()).Rack$(
                                                                           ));
            this.set$score((int) 0);
            return (Player) this.$getProxy();
        }
        
        public java.lang.String getRack() {
            {
                fabric.worker.transaction.TransactionManager $tm22 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff23 = 1;
                $label18: for (boolean $commit19 = false; !$commit19; ) {
                    if ($backoff23 > 32) {
                        while (true) {
                            try {
                                java.lang.Thread.sleep($backoff23);
                                break;
                            }
                            catch (java.lang.InterruptedException $e20) {  }
                        }
                    }
                    if ($backoff23 < 5000) $backoff23 *= 2;
                    $commit19 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        java.lang.String rack = "";
                        for (int i = 0; i < this.get$rack().get$tiles().size();
                             i++) {
                            rack += this.get$rack().get$tiles().get(i);
                        }
                        return rack;
                    }
                    catch (final fabric.worker.RetryException $e20) {
                        $commit19 = false;
                        continue $label18;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e20) {
                        $commit19 = false;
                        fabric.common.TransactionID $currentTid21 =
                          $tm22.getCurrentTid();
                        if ($e20.tid.isDescendantOf($currentTid21))
                            continue $label18;
                        if ($currentTid21.parent != null) throw $e20;
                        throw new InternalError(
                                ("Something is broken with transaction management. Got a signa" +
                                 "l to restart a different transaction than the one being mana" +
                                 "ged."));
                    }
                    catch (final Throwable $e20) {
                        $commit19 = false;
                        if ($tm22.checkForStaleObjects()) continue $label18;
                        throw new fabric.worker.AbortException($e20);
                    }
                    finally {
                        if ($commit19) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e20) {
                                $commit19 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e20) {
                                $commit19 = false;
                                fabric.common.TransactionID $currentTid21 =
                                  $tm22.getCurrentTid();
                                if ($currentTid21 == null ||
                                      $e20.tid.isDescendantOf($currentTid21) &&
                                      !$currentTid21.equals($e20.tid))
                                    continue $label18;
                                throw $e20;
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit19) {  }
                    }
                }
            }
            return null;
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new Player._Proxy(this);
        }
        
        public void $serialize(java.io.ObjectOutput out,
                               java.util.List refTypes,
                               java.util.List intraStoreRefs,
                               java.util.List interStoreRefs)
              throws java.io.IOException {
            super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            $writeRef($getStore(), this.rack, refTypes, out, intraStoreRefs,
                      interStoreRefs);
            out.writeInt(this.score);
        }
        
        public _Impl(fabric.worker.Store store, long onum, int version,
                     long expiry, fabric.worker.Store labelStore,
                     long labelOnum, fabric.worker.Store accessPolicyStore,
                     long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException, java.lang.ClassNotFoundException {
            super(store, onum, version, expiry, labelStore, labelOnum,
                  accessPolicyStore, accessPolicyOnum, in, refTypes,
                  intraStoreRefs, interStoreRefs);
            this.rack = (Rack)
                          $readRef(Rack._Proxy.class,
                                   (fabric.common.RefTypeEnum) refTypes.next(),
                                   in, store, intraStoreRefs, interStoreRefs);
            this.score = in.readInt();
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            Player._Impl src = (Player._Impl) other;
            this.rack = src.rack;
            this.score = src.score;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        final class _Proxy extends fabric.lang.Object._Proxy
          implements Player._Static {
            public _Proxy(Player._Static._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final Player._Static $instance;
            
            static {
                Player.
                  _Static.
                  _Impl
                  impl =
                  (Player._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(Player._Static._Impl.class);
                $instance = (Player._Static) impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl implements Player._Static {
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store, long onum, int version,
                         long expiry, fabric.worker.Store labelStore,
                         long labelOnum, fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException, java.lang.ClassNotFoundException {
                super(store, onum, version, expiry, labelStore, labelOnum,
                      accessPolicyStore, accessPolicyOnum, in, refTypes,
                      intraStoreRefs, interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new Player._Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -61, 109, 6, -33, 33,
    70, 60, -69, 48, 46, 78, -59, -3, -121, 87, -118, 121, 44, 54, -44, -18, 62,
    44, 29, -64, 111, 44, -10, 58, 77, -52, -82 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1540879845000L;
    public static final java.lang.String jlc$ClassType$fabil =
      ("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" +
       "zv8LT2wUbR8OAAA=");
}
