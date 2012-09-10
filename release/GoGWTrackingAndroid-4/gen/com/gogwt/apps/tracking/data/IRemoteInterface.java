/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: C:\\trial\\tracking\\GoGWTrackingAndroid-4\\aidl\\com\\gogwt\\apps\\tracking\\data\\IRemoteInterface.aidl
 */
package com.gogwt.apps.tracking.data;
public interface IRemoteInterface extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.gogwt.apps.tracking.data.IRemoteInterface
{
private static final java.lang.String DESCRIPTOR = "com.gogwt.apps.tracking.data.IRemoteInterface";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.gogwt.apps.tracking.data.IRemoteInterface interface,
 * generating a proxy if needed.
 */
public static com.gogwt.apps.tracking.data.IRemoteInterface asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = (android.os.IInterface)obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.gogwt.apps.tracking.data.IRemoteInterface))) {
return ((com.gogwt.apps.tracking.data.IRemoteInterface)iin);
}
return new com.gogwt.apps.tracking.data.IRemoteInterface.Stub.Proxy(obj);
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
case TRANSACTION_getLastLocation:
{
data.enforceInterface(DESCRIPTOR);
android.location.Location _result = this.getLastLocation();
reply.writeNoException();
if ((_result!=null)) {
reply.writeInt(1);
_result.writeToParcel(reply, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
}
else {
reply.writeInt(0);
}
return true;
}
case TRANSACTION_getGPXPoint:
{
data.enforceInterface(DESCRIPTOR);
com.gogwt.apps.tracking.data.GPXPoint _result = this.getGPXPoint();
reply.writeNoException();
if ((_result!=null)) {
reply.writeInt(1);
_result.writeToParcel(reply, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
}
else {
reply.writeInt(0);
}
return true;
}
case TRANSACTION_addListener:
{
data.enforceInterface(DESCRIPTOR);
com.gogwt.apps.tracking.data.ICollectionListener _arg0;
_arg0 = com.gogwt.apps.tracking.data.ICollectionListener.Stub.asInterface(data.readStrongBinder());
this.addListener(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_removeListener:
{
data.enforceInterface(DESCRIPTOR);
com.gogwt.apps.tracking.data.ICollectionListener _arg0;
_arg0 = com.gogwt.apps.tracking.data.ICollectionListener.Stub.asInterface(data.readStrongBinder());
this.removeListener(_arg0);
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.gogwt.apps.tracking.data.IRemoteInterface
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
public android.location.Location getLastLocation() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
android.location.Location _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getLastLocation, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = android.location.Location.CREATOR.createFromParcel(_reply);
}
else {
_result = null;
}
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public com.gogwt.apps.tracking.data.GPXPoint getGPXPoint() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.gogwt.apps.tracking.data.GPXPoint _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getGPXPoint, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = com.gogwt.apps.tracking.data.GPXPoint.CREATOR.createFromParcel(_reply);
}
else {
_result = null;
}
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public void addListener(com.gogwt.apps.tracking.data.ICollectionListener listener) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((listener!=null))?(listener.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_addListener, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public void removeListener(com.gogwt.apps.tracking.data.ICollectionListener listener) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((listener!=null))?(listener.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_removeListener, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_getLastLocation = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_getGPXPoint = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_addListener = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_removeListener = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
}
public android.location.Location getLastLocation() throws android.os.RemoteException;
public com.gogwt.apps.tracking.data.GPXPoint getGPXPoint() throws android.os.RemoteException;
public void addListener(com.gogwt.apps.tracking.data.ICollectionListener listener) throws android.os.RemoteException;
public void removeListener(com.gogwt.apps.tracking.data.ICollectionListener listener) throws android.os.RemoteException;
}
