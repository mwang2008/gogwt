/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: C:\\trial\\gogwt\\GoGWTrackingAndroid\\aidl\\com\\gogwt\\apps\\tracking\\data\\ICollectionListener.aidl
 */
package com.gogwt.apps.tracking.data;
public interface ICollectionListener extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.gogwt.apps.tracking.data.ICollectionListener
{
private static final java.lang.String DESCRIPTOR = "com.gogwt.apps.tracking.data.ICollectionListener";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.gogwt.apps.tracking.data.ICollectionListener interface,
 * generating a proxy if needed.
 */
public static com.gogwt.apps.tracking.data.ICollectionListener asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = (android.os.IInterface)obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.gogwt.apps.tracking.data.ICollectionListener))) {
return ((com.gogwt.apps.tracking.data.ICollectionListener)iin);
}
return new com.gogwt.apps.tracking.data.ICollectionListener.Stub.Proxy(obj);
}
public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_handleLocationUpdated:
{
data.enforceInterface(DESCRIPTOR);
this.handleLocationUpdated();
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.gogwt.apps.tracking.data.ICollectionListener
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
public void handleLocationUpdated() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_handleLocationUpdated, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_handleLocationUpdated = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
}
public void handleLocationUpdated() throws android.os.RemoteException;
}
